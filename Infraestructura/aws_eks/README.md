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
<td><code>init.image.repository</code></td>
<td>Init image repository</td>
<td><code>alpine</code></td>
</tr>
<tr>
<td><code>init.image.tag</code></td>
<td>Init image tag</td>
<td><code>3.8</code></td>
</tr>
<tr>
<td><code>init.image.pullPolicy</code></td>
<td>Init image pull policy</td>
<td><code>IfNotPresent</code></td>
</tr>
<tr>
<td><code>clusterDomain</code></td>
<td>The internal Kubernetes cluster domain</td>
<td><code>cluster.local</code></td>
</tr>
<tr>
<td><code>keycloak.replicas</code></td>
<td>The number of Keycloak replicas</td>
<td><code>1</code></td>
</tr>
<tr>
<td><code>keycloak.image.repository</code></td>
<td>The Keycloak image repository</td>
<td><code>jboss/keycloak</code></td>
</tr>
<tr>
<td><code>keycloak.image.tag</code></td>
<td>The Keycloak image tag</td>
<td><code>5.0.0</code></td>
</tr>
<tr>
<td><code>keycloak.image.pullPolicy</code></td>
<td>The Keycloak image pull policy</td>
<td><code>IfNotPresent</code></td>
</tr>
<tr>
<td><code>keycloak.image.pullSecrets</code></td>
<td>Image pull secrets</td>
<td><code>[]</code></td>
</tr>
<tr>
<td><code>keycloak.basepath</code></td>
<td>Path keycloak is hosted at</td>
<td><code>auth</code></td>
</tr>
<tr>
<td><code>keycloak.username</code></td>
<td>Username for the initial Keycloak admin user</td>
<td><code>keycloak</code></td>
</tr>
<tr>
<td><code>keycloak.password</code></td>
<td>Password for the initial Keycloak admin user (if <code>keycloak.existingSecret=""</code>). If not set, a random 10 characters password is created</td>
<td><code>""</code></td>
</tr>
<tr>
<td><code>keycloak.existingSecret</code></td>
<td>Specifies an existing secret to be used for the admin password</td>
<td><code>""</code></td>
</tr>
<tr>
<td><code>keycloak.existingSecretKey</code></td>
<td>The key in <code>keycloak.existingSecret</code> that stores the admin password</td>
<td><code>password</code></td>
</tr>
<tr>
<td><code>keycloak.extraInitContainers</code></td>
<td>Additional init containers, e. g. for providing themes, etc. Passed through the <code>tpl</code> function and thus to be configured a string</td>
<td><code>""</code></td>
</tr>
<tr>
<td><code>keycloak.extraContainers</code></td>
<td>Additional sidecar containers, e. g. for a database proxy, such as Google's cloudsql-proxy. Passed through the <code>tpl</code> function and thus to be configured a string</td>
<td><code>""</code></td>
</tr>
<tr>
<td><code>keycloak.extraEnv</code></td>
<td>Allows the specification of additional environment variables for Keycloak. Passed through the <code>tpl</code> function and thus to be configured a string</td>
<td><code>""</code></td>
</tr>
<tr>
<td><code>keycloak.extraVolumeMounts</code></td>
<td>Add additional volumes mounts, e. g. for custom themes. Passed through the <code>tpl</code> function and thus to be configured a string</td>
<td><code>""</code></td>
</tr>
<tr>
<td><code>keycloak.extraVolumes</code></td>
<td>Add additional volumes, e. g. for custom themes. Passed through the <code>tpl</code> function and thus to be configured a string</td>
<td><code>""</code></td>
</tr>
<tr>
<td><code>keycloak.extraPorts</code></td>
<td>Add additional ports, e. g. for custom admin console port. Passed through the <code>tpl</code> function and thus to be configured a string</td>
<td><code>""</code></td>
</tr>
<tr>
<td><code>keycloak.podDisruptionBudget</code></td>
<td>Pod disruption budget</td>
<td><code>{}</code></td>
</tr>
<tr>
<td><code>keycloak.priorityClassName</code></td>
<td>Pod priority classname</td>
<td><code>{}</code></td>
</tr>
<tr>
<td><code>keycloak.resources</code></td>
<td>Pod resource requests and limits</td>
<td><code>{}</code></td>
</tr>
<tr>
<td><code>keycloak.affinity</code></td>
<td>Pod affinity. Passed through the <code>tpl</code> function and thus to be configured a string</td>
<td><code>Hard node and soft zone anti-affinity</code></td>
</tr>
<tr>
<td><code>keycloak.nodeSelector</code></td>
<td>Node labels for pod assignment</td>
<td><code>{}</code></td>
</tr>
<tr>
<td><code>keycloak.tolerations</code></td>
<td>Node taints to tolerate</td>
<td><code>[]</code></td>
</tr>
<tr>
<td><code>keycloak.podLabels</code></td>
<td>Extra labels to add to pod</td>
<td><code>{}</code></td>
</tr>
<tr>
<td><code>keycloak.podAnnotations</code></td>
<td>Extra annotations to add to pod</td>
<td><code>{}</code></td>
</tr>
<tr>
<td><code>keycloak.hostAliases</code></td>
<td>Mapping between IP and hostnames that will be injected as entries in the pod's hosts files</td>
<td><code>[]</code></td>
</tr>
<tr>
<td><code>keycloak.securityContext</code></td>
<td>Security context for the pod</td>
<td><code>{runAsUser: 1000, fsGroup: 1000, runAsNonRoot: true}</code></td>
</tr>
<tr>
<td><code>keycloak.preStartScript</code></td>
<td>Custom script to run before Keycloak starts up</td>
<td>``</td>
</tr>
<tr>
<td><code>keycloak.lifecycleHooks</code></td>
<td>Container lifecycle hooks. Passed through the <code>tpl</code> function and thus to be configured a string</td>
<td>``</td>
</tr>
<tr>
<td><code>keycloak.extraArgs</code></td>
<td>Additional arguments to the start command</td>
<td>``</td>
</tr>
<tr>
<td><code>keycloak.livenessProbe.initialDelaySeconds</code></td>
<td>Liveness Probe <code>initialDelaySeconds</code></td>
<td><code>120</code></td>
</tr>
<tr>
<td><code>keycloak.livenessProbe.timeoutSeconds</code></td>
<td>Liveness Probe <code>timeoutSeconds</code></td>
<td><code>5</code></td>
</tr>
<tr>
<td><code>keycloak.readinessProbe.initialDelaySeconds</code></td>
<td>Readiness Probe <code>initialDelaySeconds</code></td>
<td><code>30</code></td>
</tr>
<tr>
<td><code>keycloak.readinessProbe.timeoutSeconds</code></td>
<td>Readiness Probe <code>timeoutSeconds</code></td>
<td><code>1</code></td>
</tr>
<tr>
<td><code>keycloak.cli.nodeIdentifier</code></td>
<td>WildFly CLI script for setting the node identifier</td>
<td>See <code>values.yaml</code></td>
</tr>
<tr>
<td><code>keycloak.cli.logging</code></td>
<td>WildFly CLI script for logging configuration</td>
<td>See <code>values.yaml</code></td>
</tr>
<tr>
<td><code>keycloak.cli.reverseProxy</code></td>
<td>WildFly CLI script for reverse proxy configuration</td>
<td>See <code>values.yaml</code></td>
</tr>
<tr>
<td><code>keycloak.cli.ha</code></td>
<td>Settings for HA setups</td>
<td>See <code>values.yaml</code></td>
</tr>
<tr>
<td><code>keycloak.cli.custom</code></td>
<td>Additional custom WildFly CLI script</td>
<td><code>""</code></td>
</tr>
<tr>
<td><code>keycloak.service.annotations</code></td>
<td>Annotations for the Keycloak service</td>
<td><code>{}</code></td>
</tr>
<tr>
<td><code>keycloak.service.labels</code></td>
<td>Additional labels for the Keycloak service</td>
<td><code>{}</code></td>
</tr>
<tr>
<td><code>keycloak.service.type</code></td>
<td>The service type</td>
<td><code>ClusterIP</code></td>
</tr>
<tr>
<td><code>keycloak.service.port</code></td>
<td>The service port</td>
<td><code>80</code></td>
</tr>
<tr>
<td><code>keycloak.service.nodePort</code></td>
<td>The node port used if the service is of type <code>NodePort</code></td>
<td><code>""</code></td>
</tr>
<tr>
<td><code>keycloak.ingress.enabled</code></td>
<td>if <code>true</code>, an ingress is created</td>
<td><code>false</code></td>
</tr>
<tr>
<td><code>keycloak.ingress.annotations</code></td>
<td>annotations for the ingress</td>
<td><code>{}</code></td>
</tr>
<tr>
<td><code>keycloak.ingress.path</code></td>
<td>if <code>true</code>, an ingress is created</td>
<td><code>/</code></td>
</tr>
<tr>
<td><code>keycloak.ingress.hosts</code></td>
<td>a list of ingress hosts</td>
<td><code>[keycloak.example.com]</code></td>
</tr>
<tr>
<td><code>keycloak.ingress.tls</code></td>
<td>a list of <a href="https://v1-9.docs.kubernetes.io/docs/reference/generated/kubernetes-api/v1.9/#ingresstls-v1beta1-extensions" rel="nofollow">IngressTLS</a> items</td>
<td><code>[]</code></td>
</tr>
<tr>
<td><code>keycloak.persistence.deployPostgres</code></td>
<td>If true, the PostgreSQL chart is installed</td>
<td><code>false</code></td>
</tr>
<tr>
<td><code>keycloak.persistence.existingSecret</code></td>
<td>Name of an existing secret to be used for the database password (if <code>keycloak.persistence.deployPostgres=false</code>). Otherwise a new secret is created</td>
<td><code>""</code></td>
</tr>
<tr>
<td><code>keycloak.persistence.existingSecretKey</code></td>
<td>The key for the database password in the existing secret (if <code>keycloak.persistence.deployPostgres=false</code>)</td>
<td><code>password</code></td>
</tr>
<tr>
<td><code>keycloak.persistence.dbVendor</code></td>
<td>One of <code>h2</code>, <code>postgres</code>, <code>mysql</code>, or <code>mariadb</code> (if <code>deployPostgres=false</code>)</td>
<td><code>h2</code></td>
</tr>
<tr>
<td><code>keycloak.persistence.dbName</code></td>
<td>The name of the database to connect to (if <code>deployPostgres=false</code>)</td>
<td><code>keycloak</code></td>
</tr>
<tr>
<td><code>keycloak.persistence.dbHost</code></td>
<td>The database host name (if <code>deployPostgres=false</code>)</td>
<td><code>mykeycloak</code></td>
</tr>
<tr>
<td><code>keycloak.persistence.dbPort</code></td>
<td>The database host port (if <code>deployPostgres=false</code>)</td>
<td><code>5432</code></td>
</tr>
<tr>
<td><code>keycloak.persistence.dbUser</code></td>
<td>The database user (if <code>deployPostgres=false</code>)</td>
<td><code>keycloak</code></td>
</tr>
<tr>
<td><code>keycloak.persistence.dbPassword</code></td>
<td>The database password (if <code>deployPostgres=false</code>)</td>
<td><code>""</code></td>
</tr>
<tr>
<td><code>postgresql.postgresUser</code></td>
<td>The PostgreSQL user (if <code>keycloak.persistence.deployPostgres=true</code>)</td>
<td><code>keycloak</code></td>
</tr>
<tr>
<td><code>postgresql.postgresPassword</code></td>
<td>The PostgreSQL password (if <code>keycloak.persistence.deployPostgres=true</code>)</td>
<td><code>""</code></td>
</tr>
<tr>
<td><code>postgresql.postgresDatabase</code></td>
<td>The PostgreSQL database (if <code>keycloak.persistence.deployPostgres=true</code>)</td>
<td><code>keycloak</code></td>
</tr>
<tr>
<td><code>test.enabled</code></td>
<td>If <code>true</code>, test pods get scheduled</td>
<td><code>true</code></td>
</tr>
<tr>
<td><code>test.image.repository</code></td>
<td>Test image repository</td>
<td><code>unguiculus/docker-python3-phantomjs-selenium</code></td>
</tr>
<tr>
<td><code>test.image.tag</code></td>
<td>Test image tag</td>
<td><code>v1</code></td>
</tr>
<tr>
<td><code>test.image.pullPolicy</code></td>
<td>Test image pull policy</td>
<td><code>IfNotPresent</code></td>
</tr>
<tr>
<td><code>test.securityContext</code></td>
<td>Security context for the test pod</td>
<td><code>{runAsUser: 1000, fsGroup: 1000, runAsNonRoot: true}</code></td>
</tr>
</tbody>
</table>