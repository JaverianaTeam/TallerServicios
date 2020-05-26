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
echo ""
echo ""
echo ""
echo "==============================================="
echo "$(date)"
echo "NodeInstanceRole: $var"

read -p "Is the NodeInstanceRole value OK, type y/n? " -n 1 -r
echo    # (optional) move to a new line
if [[ ! $REPLY =~ ^[Yy]$ ]]
then
    [[ "$0" = "$BASH_SOURCE" ]] && echo "$(date): Exit, Bye ..." && exit 1 || return 1 # handle exits from shell or function but don't exit interactive shell
fi


{
var=$(echo "$var" | sed  "s,:,\\\:,g") && echo $var && var=$(echo "$var" | sed  "s,\/,\\\/,g") && echo $var && sed -i'.original' -e "s/rolearn.*/rolearn\:\ $var/g" aws-auth-cm.yaml && echo "$(date): aws-auth-cm.yaml edited" && kubectl apply -f aws-auth-cm.yaml 
} || { # catch
     echo "$(date): Error Applying the AWS authenticator configuration map...";
     echo "Try manually, it is required to join Worker Nodes to the EKS cluster."
     exit -3
}



echo " $(date): AWS authenticator configuration Applied"
echo "Removing temporal files.."
rm -fr aws-auth-cm.yaml.original
echo "Succeed!"
