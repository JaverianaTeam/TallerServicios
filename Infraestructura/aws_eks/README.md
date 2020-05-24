<h1> Creación de cluster de Kubernetes en AWS </h1>



Para la instalación de cluster, antes se tener en cuenta cumplir los siguientes requisitos. 

Pre-requisitos:
- Instalar y configurar la awscli versión 1.16.156 o superior, para más información consulte https://docs.aws.amazon.com/eks/latest/userguide/getting-started-console.html#gs-console-install-awscli
- Crear el "Amazon EKS worker node IAM role", para más información visite https://docs.aws.amazon.com/eks/latest/userguide/getting-started-console.html 
- Instalar y configurar la herramienta kubectl, para más información visite https://docs.aws.amazon.com/eks/latest/userguide/install-kubectl.html 



<b>Cloudformation Build:</b>
```
./eks-cloudformation.sh
```


El proceso completo toma aproximadamente 15 minutos.  Finalizada la creación, se observará  un mensaje de salida como es  muestra a continuación, éste contiene información útil del cluster que se acaba de crear.  


```
==================================
Sat May 23 21:41:01 -05 2020
EKS Cluster successfully created
Cluster name: eks-ModVal-Stack2
VPC: vpc-08787760b18595541
Subnets: subnet-014e524cdcabbc7ad,subnet-0b9d3fd121ca2ab6b,subnet-0501729ed1cea8648
Security Group: sg-06ef3d2b409c8146a
Node Instance Role: arn:aws:iam::455314860156:role/worker-nodes-ModVal-Stack2-NodeInstanceRole-QTSDDA41024T
==================================
``` 

Primero crea una nueva VPC, luego crea el cluster EKS y finalmente el grupo de Worker Nodes. El grupo de worker nodes está compuesto por 2 instancias EC2 activas t2.medium , con la posibilidad de crecer hasta 4 dependiendo de la carga. 

<p>Una vez finalizada la creación de cluster EKS de Kubernetes, se debe adicionar el NodeInstanceRole a la configuración del cluster para permitir a los worker nodes unirse al cluster. Para automatizar este proceso se creó un script que hace esta tarea automáticamente. El script recibe como argumento el NodeInstanceRole, descarga un template de configuración, modifica el NodeInstanceRole correcto y lo aplica a la configuración del cluster.</p>

<p><b>Nota:</b> se requiere haber configurado previamente la herramienta kubectl, apuntando al cluster creado en pasos anteriores. Para validar el contexto actual del kubectl, ejecutar el siguiente comando. </p>
 
```
kubectl config current-context
```

<p>Para adicionar el NodeInstanceRole a la configuración ejecute el siguiente comando.</p>

```
./apply-aws-authenticator.sh <NodeInstanceRole>
```

Para validar si los Worker Nodes se adicionaron correctamente al cluster, ejecute el siguiente comando. Como salida obtendrá los nodos activos en el cluster. 

```
kubectl get nodes
```

<p>Ejemplo de salida:</p>

```
bash-3.2$ kubectl get nodes
NAME                              STATUS   ROLES    AGE   VERSION
ip-192-169-133-155.ec2.internal   Ready    <none>   37m   v1.14.9-eks-1f0ca9
ip-192-169-254-222.ec2.internal   Ready    <none>   37m   v1.14.9-eks-1f0ca9
bash-3.2$
```

<p>Una vez compleados estos pasos, el cluster de Kubernetes está listo para que podamos desplegar las aplicaciones.</p>


<h2>Parámetros para script eks-cloudformation </h2>

<p>El script eks-cloudformation.sh es totalmente configurable para ser utilizado en cualquier cuenta de AWS, región, tipo de instancias y más. </p>

<p>
La siguiente tabla lista los parámetros configurables del eks-cloudformation script y sus valores default.</p>





<table>
<thead>
<tr>
<th>Parameter</th>
<th>Description</th>
<th>Default</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>STACK_NAME</code></td>
<td>Nombre para los stacks creados cloudformation</td>
<td><code>ModVal-Stack</code></td>
</tr>
<tr>
<td><code>AWS_PROFILE</code></td>
<td>Nombre del perfil configurado en AWS credentials</td>
<td><code>""</code></td>
</tr>
<tr>
<td><code>AWS_REGION</code></td>
<td>Región donde se creará el cluster EKS y sus componentes</td>
<td><code>us-east-1</code></td>
</tr>
<tr>
<td><code>VPC_STACK_TEMPLATE</code></td>
<td>Template para creación de la VPC con subnets públicas</td>
<td><code>amazon-eks-vpc-sample.yaml</code></td>
</tr>
<tr>
<td><code>VPC_SUBNET</code></td>
<td>Rango CIDR para la nueva VPC.</td>
<td><code>192.169.0.0/16</code></td>
</tr>
<tr>
<td><code>VPC_SUBNET_B1</code></td>
<td>Rango de CIDR para la subred pública 1</td>
<td><code>192.169.64.0/18</code></td>
</tr>
<tr>
<td><code>VPC_SUBNET_B2</code></td>
<td>Rango de CIDR para la subred pública 2</td>
<td><code>192.169.128.0/18</code></td>
</tr>
<tr>
<td><code>VPC_SUBNET_B3</code></td>
<td>Rango de CIDR para la subred pública 3</td>
<td><code>192.169.192.0/18</code></td>
</tr>
<tr>
<td><code>ARN_ROLE</code></td>
<td>Rol requerido para la configuración del clúster EKS</td>
<td><code>""</code></td>
</tr>
<tr>
<td><code>WN_STACK_TEMPLATE</code></td>
<td>Template para creación de Worker Nodes</td>
<td><code>amazon-eks-nodegroup.yaml</code></td>
</tr>
<tr>
<td><code>EC2_DISK_SIZE</code></td>
<td>Tamaño de disco para instancias EC2 que componen el Work Group</td>
<td><code>20 GB</code></td>
</tr>
<tr>
<td><code>EC2_INSTANCE_TYPE</code></td>
<td>Tipo de Instancia EC2 del Work Group</td>
<td><code>t2.medium</code></td>
</tr>
<tr>
<td><code>EC2_IMAGE_ID</code></td>
<td>AMIID - ID de imágen Sistema operativo para Worker Nodes </td>
<td><code>ami-0f15d55736fd476da (Amazon Linix 2)</code></td>
</tr>
<tr>
<td><code>SCALING_MIN_SIZE</code></td>
<td>Número mínimo de instancias EC2 en el Work Group</td>
<td><code>1</code></td>
</tr>
<tr>
<td><code>SCALING_MAX_SIZE</code></td>
<td>Número máximo de instancias EC2 en el Work Group</td>
<td><code>4</code></td>
</tr>
<tr>
<td><code>AWS_KEY_NAME</code></td>
<td>Número deseado de instancias EC2 en el Work Group</td>
<td><code>2</code></td>
</tr>
</tbody>
</table>