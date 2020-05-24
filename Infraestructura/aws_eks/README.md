<h1> Creación de cluster de Kubernetes en AWS </h1>



Para la instalación de cluster, antes se tener en cuenta cumplir los siguientes requisitos. 

Pre-requisitos:
- Instalar y configurar la awscli versión 1.16.156 o superior, para más información consulte https://docs.aws.amazon.com/eks/latest/userguide/getting-started-console.html#gs-console-install-awscli
- Crear el "Amazon EKS worker node IAM role", para más información visite https://docs.aws.amazon.com/eks/latest/userguide/getting-started-console.html 
- Instalar y configurar la herramienta kubectl, para más información visite https://docs.aws.amazon.com/eks/latest/userguide/install-kubectl.html 


Una vez se cumpla con todos los requisitos anteriormente mencionados, se puede proceder a ejecutar el script de inicio, este script de inicio está compuesto por varios scripts de cloudformation con lo cuales se crea todos los componentes requeridos por el cluster de Kubernetes en AWS.

Cloudformation Build:
`./eks-cloudformation.sh`
