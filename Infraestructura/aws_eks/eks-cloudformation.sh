aws cloudformation --region us-east-1 create-stack --stack-name vpn-4-test \
--template-url https://amazon-eks.s3.us-west-2.amazonaws.com/cloudformation/2020-05-08/amazon-eks-vpc-sample.yaml \
--parameters ParameterKey=VpcBlock,ParameterValue=192.169.0.0/16 \
 ParameterKey=Subnet01Block,ParameterValue=192.169.64.0/18 \
 ParameterKey=Subnet02Block,ParameterValue=192.169.128.0/18 \
 ParameterKey=Subnet03Block,ParameterValue=192.169.192.0/18 \
 --capabilities CAPABILITY_IAM \
--profile hneral



aws cloudformation describe-stack-events --profile hneral --region us-east-1  --stack-name   vpn-4-test --query "StackEvents"



EKS

aws eks --region us-east-1 create-cluster --name eks-ModVal \
--role-arn arn:aws:iam::455314860156:role/eksClusterRole \
--resources-vpc-config subnetIds=subnet-0f35595c184c8ae8a,subnet-095e50b61b97eb1df,subnet-03513a7039863088a,securityGroupIds=sg-0e946ed06def4371f \
--profile hneral


aws eks --region us-east-1 describe-cluster --name eks-ModVal --query cluster.status --profile turbot__devnitro


Worker Nodes


aws cloudformation --region us-east-1 create-stack --stack-name modVal-eks-cluster-worker-nodes-stack \
--template-body file://amazon-eks-nodegroup.yaml \
--parameters ParameterKey=VpcId,ParameterValue=vpc-09a13e23eea64e950 \
ParameterKey=Subnets,ParameterValue=subnet-0f35595c184c8ae8a\\,subnet-03513a7039863088a\\,subnet-095e50b61b97eb1df \
ParameterKey=NodeVolumeSize,ParameterValue=20 \
ParameterKey=NodeInstanceType,ParameterValue=t2.medium \
ParameterKey=NodeImageId,ParameterValue=ami-0f15d55736fd476da \
ParameterKey=NodeGroupName,ParameterValue=modVal-eks-cluster-node-group \
ParameterKey=NodeAutoScalingGroupMinSize,ParameterValue=1 \
ParameterKey=NodeAutoScalingGroupMaxSize,ParameterValue=4 \
ParameterKey=NodeAutoScalingGroupDesiredCapacity,ParameterValue=2 \
ParameterKey=KeyName,ParameterValue=hneral \
ParameterKey=ClusterName,ParameterValue=eks-ModVal \
ParameterKey=ClusterControlPlaneSecurityGroup,ParameterValue=sg-0e946ed06def4371f \
--capabilities CAPABILITY_IAM \
--profile hneral



aws cloudformation describe-stack-events --profile hneral --region us-east-1  --stack-name   modVal-eks-cluster-worker-nodes-stack  --query "StackEvents[0]"


	
Set the context for kubectl 


aws eks --region us-east-1 update-kubeconfig --name eks-ModVal --profile hneral




Crear el Authentication
Open the file with your favorite text editor. Replace the <ARN of instance role (not instance profile)> snippet with the NodeInstanceRole value that you recorded in the previous procedure, and save the file.

curl -o aws-auth-cm.yaml https://amazon-eks.s3.us-west-2.amazonaws.com/cloudformation/2020-03-23/aws-auth-cm.yaml


Apply the configuration. This command may take a few minutes to finish.
kubectl apply -f aws-auth-cm.yaml


	