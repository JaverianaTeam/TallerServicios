#!/bin/bash

##Script for creating a Kubernetes cluster on AWS.
##Enterprise Software Architecture Specialization
##Architecture Modeling and Validation

echo $(date)
echo "Script for creating a Kubernetes cluster on AWS"
echo "Enterprise Software Architecture Specialization"
echo "Architecture Modeling and Validation - Course"
echo "Taller - Servicios"
echo "Universidad Javeriana - Colombia"

### General Configuration variables
STACK_NAME="ModVal-StackT"
AWS_PROFILE="hneral"
AWS_REGION="us-east-1"

### VPC Configuration variables
VPC_STACK_TEMPLATE="https://amazon-eks.s3.us-west-2.amazonaws.com/cloudformation/2020-05-08/amazon-eks-vpc-sample.yaml"
VPC_SUBNET="192.169.0.0/16"
VPC_SUBNET_B1="192.169.64.0/18"
VPC_SUBNET_B2="192.169.128.0/18"
VPC_SUBNET_B3="192.169.192.0/18"


### EKS Cluster configuration 
ARN_ROLE="arn:aws:iam::455314860156:role/eksClusterRole"



### Worker Nodes configuration 
WN_STACK_TEMPLATE="file://amazon-eks-nodegroup.yaml"
EC2_DISK_SIZE="20"
EC2_INSTANCE_TYPE="t2.medium"
EC2_IMAGE_ID="ami-0f15d55736fd476da"
SCALING_MIN_SIZE="1"
SCALING_MAX_SIZE="4"
SCALING_DES_SIZE="2"
AWS_KEY_NAME="hneral"

## Creating a VPC for your Amazon EKS cluster - Only public subnets
#Based on a published official VPC template. 

{ # trying to create a new subnet.

aws cloudformation --region ${AWS_REGION} create-stack --stack-name vpc-${STACK_NAME} --template-url  ${VPC_STACK_TEMPLATE} --parameters ParameterKey=VpcBlock,ParameterValue=${VPC_SUBNET} ParameterKey=Subnet01Block,ParameterValue=${VPC_SUBNET_B1} ParameterKey=Subnet02Block,ParameterValue=${VPC_SUBNET_B2} ParameterKey=Subnet03Block,ParameterValue=${VPC_SUBNET_B3} --capabilities CAPABILITY_IAM --profile ${AWS_PROFILE}  && echo "Creating a new VPC"
    #save your output

} || { # catch
     echo "Error creating the VPC - Cloudformation VPC command syntax error";
     exit -10
}

##Wait until the PVC creation finishes. 
#aws cloudformation wait stack-create-complete --stack-name vpn-test --region us-east-1 --profile hneral
 # Wait for create-stack to finish
    echo  "$(date): Waiting for create-stack command to complete";
    CREATE_STACK_STATUS=$(aws --region ${AWS_REGION} --profile ${AWS_PROFILE} cloudformation describe-stacks --stack-name vpc-${STACK_NAME} --query 'Stacks[0].StackStatus' --output text);
    while [[ $CREATE_STACK_STATUS == "REVIEW_IN_PROGRESS" ]] || [[ $CREATE_STACK_STATUS == "CREATE_IN_PROGRESS" ]]; do
        # Wait 30 seconds and then check stack status again
        sleep 30;
        CREATE_STACK_STATUS=$(aws --region ${AWS_REGION} --profile ${AWS_PROFILE} cloudformation describe-stacks --stack-name vpc-${STACK_NAME} --query 'Stacks[0].StackStatus' --output text);
        echo "$(date): Wait for VPC create-stack to finish...";
    done

CREATE_STACK_STATUS=$(aws --region ${AWS_REGION} --profile ${AWS_PROFILE} cloudformation describe-stacks --stack-name vpc-${STACK_NAME} --query 'Stacks[0].StackStatus' --output text);
if [[ $CREATE_STACK_STATUS -eq "CREATE_COMPLETE" ]]; then
echo "$(date): A VPC was created successfully";
else
echo "$(date): VPC Creating Failed";
exit -9;
fi



### Getting VPC outputs
AWS_SG=$(aws --region ${AWS_REGION} --profile ${AWS_PROFILE} cloudformation describe-stacks --stack-name vpc-${STACK_NAME} --query 'Stacks[0].Outputs[?OutputKey==`SecurityGroups`].OutputValue' --output text);
AWS_VPC_SUBNETS=$(aws --region ${AWS_REGION} --profile ${AWS_PROFILE} cloudformation describe-stacks --stack-name vpc-${STACK_NAME} --query 'Stacks[0].Outputs[?OutputKey==`SubnetIds`].OutputValue' --output text);
AWS_VPC_ID=$(aws --region ${AWS_REGION} --profile ${AWS_PROFILE} cloudformation describe-stacks --stack-name vpc-${STACK_NAME} --query 'Stacks[0].Outputs[?OutputKey==`VpcId`].OutputValue' --output text);

### Creating the EKS cluster
### Creating the EKS cluster
### Creating the EKS cluster
### Creating the EKS cluster
### Creating the EKS cluster



{ ### Creating the EKS cluster - Trying
aws eks --region ${AWS_REGION} create-cluster --name eks-${STACK_NAME} --role-arn ${ARN_ROLE} --resources-vpc-config subnetIds=${AWS_VPC_SUBNETS},securityGroupIds=${AWS_SG} --profile ${AWS_PROFILE} && echo "Creating a new EKS Cluster"
} || { # catch
     echo "Error creating the EKS Cluster - AWS EKS command syntax error";
     exit -8
}

##Wait until the EKS Cluster creation finishes. 
 # Wait for create-stack to finish
    echo  "$(date): Waiting for EKS create-stack command to complete";
    CREATE_EKS_STATUS=$(aws eks --region ${AWS_REGION} describe-cluster --name eks-${STACK_NAME} --query cluster.status --profile ${AWS_PROFILE})
    while [[ $CREATE_EKS_STATUS == '"CREATING"' ]]; do
        # Wait 30 seconds and then check stack status again
        sleep 30;
        CREATE_EKS_STATUS=$(aws eks --region ${AWS_REGION} describe-cluster --name eks-${STACK_NAME} --query cluster.status --profile ${AWS_PROFILE})
        echo "$(date):Wait for EKS create to finish...";
    done

CREATE_EKS_STATUS=$(aws eks --region ${AWS_REGION} describe-cluster --name eks-${STACK_NAME} --query cluster.status --profile ${AWS_PROFILE})
if [[ $CREATE_EKS_STATUS == '"ACTIVE"' ]]; then
echo "$(date): A EKS Cluster was created successfully";
else
echo "$(date): EKS Creating Failed";
exit -7;
fi



### Creating the EKS Worker Nodes
### Creating the EKS Worker Nodes
### Creating the EKS Worker Nodes
### Creating the EKS Worker Nodes
### Creating the EKS Worker Nodes
### Creating the EKS Worker Nodes


#Filter Subnets 
echo "Getting Subnet IDs"
SUBNET_ID1=$(aws --region ${AWS_REGION} --profile ${AWS_PROFILE} cloudformation describe-stacks --stack-name vpc-${STACK_NAME} --query 'Stacks[0].Outputs[?OutputKey==`SubnetIds`].OutputValue' --output text | awk -F',' '{print $1}')
SUBNET_ID2=$(aws --region ${AWS_REGION} --profile ${AWS_PROFILE} cloudformation describe-stacks --stack-name vpc-${STACK_NAME} --query 'Stacks[0].Outputs[?OutputKey==`SubnetIds`].OutputValue' --output text | awk -F',' '{print $2}')
SUBNET_ID3=$(aws --region ${AWS_REGION} --profile ${AWS_PROFILE} cloudformation describe-stacks --stack-name vpc-${STACK_NAME} --query 'Stacks[0].Outputs[?OutputKey==`SubnetIds`].OutputValue' --output text | awk -F',' '{print $3}')


{ ### Creating the EKS Worker Nodes - Trying
aws cloudformation --region ${AWS_REGION} create-stack --stack-name worker-nodes-${STACK_NAME}  --template-body ${WN_STACK_TEMPLATE}  --parameters ParameterKey=VpcId,ParameterValue=${AWS_VPC_ID} ParameterKey=Subnets,ParameterValue=${SUBNET_ID1}\\,${SUBNET_ID2}\\,${SUBNET_ID3} ParameterKey=NodeVolumeSize,ParameterValue=${EC2_DISK_SIZE} ParameterKey=NodeInstanceType,ParameterValue=${EC2_INSTANCE_TYPE} ParameterKey=NodeImageId,ParameterValue=${EC2_IMAGE_ID} ParameterKey=NodeGroupName,ParameterValue=eks-node-group-${STACK_NAME} ParameterKey=NodeAutoScalingGroupMinSize,ParameterValue=${SCALING_MIN_SIZE} ParameterKey=NodeAutoScalingGroupMaxSize,ParameterValue=${SCALING_MAX_SIZE} ParameterKey=NodeAutoScalingGroupDesiredCapacity,ParameterValue=${SCALING_DES_SIZE} ParameterKey=KeyName,ParameterValue=${AWS_KEY_NAME} ParameterKey=ClusterName,ParameterValue=eks-${STACK_NAME} ParameterKey=ClusterControlPlaneSecurityGroup,ParameterValue=${AWS_SG} --capabilities CAPABILITY_IAM --profile ${AWS_PROFILE} && echo "Creating the EKS Worker Nodes"
} || { # catch
     echo "Error creating the EKS Worker Nodes - AWS cloudformation command syntax error";
     exit -6
}


##Wait until the Worker Nodes creation finishes. 
 # Wait for create-stack to finish
    echo  "$(date): Waiting for create-stack command to complete";
    CREATE_STACK_STATUS=$(aws --region ${AWS_REGION} --profile ${AWS_PROFILE} cloudformation describe-stacks --stack-name worker-nodes-${STACK_NAME} --query 'Stacks[0].StackStatus' --output text);
    while [[ $CREATE_STACK_STATUS == "REVIEW_IN_PROGRESS" ]] || [[ $CREATE_STACK_STATUS == "CREATE_IN_PROGRESS" ]]; do
        # Wait 30 seconds and then check stack status again
        sleep 30;
        CREATE_STACK_STATUS=$(aws --region ${AWS_REGION} --profile ${AWS_PROFILE} cloudformation describe-stacks --stack-name worker-nodes-${STACK_NAME} --query 'Stacks[0].StackStatus' --output text);
        echo "$(date): Wait for Worker Nodes create-stack to finish...";
    done

CREATE_STACK_STATUS=$(aws --region ${AWS_REGION} --profile ${AWS_PROFILE} cloudformation describe-stacks --stack-name worker-nodes-${STACK_NAME} --query 'Stacks[0].StackStatus' --output text);
if [[ $CREATE_STACK_STATUS -eq "CREATE_COMPLETE" ]]; then
echo "$(date): The Worker Nodes were successfully created ";
else
echo "$(date): The Worker Nodes Creating Failed";
exit -5;
fi



### Getting the NodeInstanceRole

NODE_INSTANCE_ROLE=$(aws --region ${AWS_REGION} --profile ${AWS_PROFILE} cloudformation describe-stacks --stack-name worker-nodes-${STACK_NAME} --query 'Stacks[0].Outputs[?OutputKey==`NodeInstanceRole`].OutputValue' --output text)

echo "=================================="
echo "$(date)"
echo "EKS Cluster successfully created"
echo "Cluster name: eks-${STACK_NAME}"
echo "VPC: ${AWS_VPC_ID}"
echo "Subnets: ${AWS_VPC_SUBNETS}"
echo "Security Group: ${AWS_SG}"
echo "Node Instance Role: ${NODE_INSTANCE_ROLE}"
echo "=================================="


#Trying to configure automatically the kubectl.
echo "Trying to update the kubeconfig... "
{
aws eks --region ${AWS_REGION} update-kubeconfig --name eks-${STACK_NAME} --profile ${AWS_PROFILE} && echo "Creating a new kubeconfig"
} || { # catch
     echo "Error configuring updating  the kubeconfig  - Error";
     echo "Do not forget to configure the kubectl tool..."
     exit -4
}

echo "$(date): Kubeconfig was set, Enjoy..."
