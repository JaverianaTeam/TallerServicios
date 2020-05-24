#!/bin/bash


echo "$(date): Checking existence of input arguments..." 

if [ -z "$1" ]
  then
    echo "No argument supplied"
    echo "usage: ./apply-aws-authenticator.sh <NodeInstanceRole>"
    exit -1;
fi

echo " Getting the aws-auth-cm.yaml file."

rm -fr aws-auth-cm.yaml;

{
curl -o aws-auth-cm.yaml https://amazon-eks.s3.us-west-2.amazonaws.com/cloudformation/2020-03-23/aws-auth-cm.yaml && echo "the aws-auth-cm.yaml was downloaded"
} || { # catch
     echo "$(date): Error: Getting the aws-auth-cm.yaml file";
     exit -2
}


echo "Applying the AWS authenticator configuration map..." 

var=$1
{
echo "NodeInstanceRole: $var" && var=$(echo "$var" | sed  "s,:,\\\:,g") && echo $var && var=$(echo "$var" | sed  "s,\/,\\\/,g") && echo $var && sed -i'.original' -e "s/rolearn.*/rolearn\:\ $var/g" aws-auth-cm.yaml && echo "$(date): aws-auth-cm.yaml edited" && kubectl apply -f aws-auth-cm.yaml 
} || { # catch
     echo "$(date): Error Applying the AWS authenticator configuration map...";
     echo "Try manually, it is required to join Worker Nodes to the EKS cluster."
     exit -3
}



echo " $(date): AWS authenticator configuration Applied"
echo "Removing temporal files.."
rm -fr aws-auth-cm.yaml
rm -fr aws-auth-cm.yaml.original
echo "Succeed!"