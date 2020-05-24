<h1> Creación de cluster de Kubernetes en AWS </h1>



Para la instalación de cluster, antes se tener en cuenta cumplir los siguientes requisitos. 

Pre-requisitos:
- Instalar y configurar la awscli versión 1.16.156 o superior, para más información consulte https://docs.aws.amazon.com/eks/latest/userguide/getting-started-console.html#gs-console-install-awscli
- Crear el "Amazon EKS worker node IAM role", para más información visite https://docs.aws.amazon.com/eks/latest/userguide/getting-started-console.html 
- Instalar y configurar la herramienta kubectl, para más información visite https://docs.aws.amazon.com/eks/latest/userguide/install-kubectl.html 



<b>Cloudformation Build:</b>
<p>`./eks-cloudformation.sh` </p>


El proceso completo toma aproximadamente 15 minutos.  Finalizada la creación, se observará  un mensaje de salida como es  muestra a continuación, éste contiene información útil del cluster que se acaba de crear.  

<p>
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
</p>

Primero crea una nueva VPC, luego crea el cluster EKS y finalmente el grupo de Worker Nodes. El grupo de worker nodes está compuesto por 2 instancias EC2 activas t2.medium , con la posibilidad de crecer hasta 4 dependiendo de la carga. 



