#!/bin/bash

##Script for deploying service on the EKs cluster.
##Enterprise Software Architecture Specialization
##Architecture Modeling and Validation



### Creamos el ingress controller en el Cluster



INGRESS=$(kubectl get pods -A | grep -i ingress | awk '{print($1)}');

if [ -z "$INGRESS" ]
then
      echo "$(date): Adding Ingress Controller Service..."
      kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/master/deploy/static/provider/aws/deploy.yaml
fi


helm delete my-confluent --purge
helm delete pagos-app --purge
helm delete usuario-app --purge
helm delete convenio-app  --purge
helm delete notificacion-app --purge

TILLER_ACCOUNT=$(kubectl get serviceaccount -n kube-system | grep tiller | awk '{print($1)}');

## Config helm cli and tiller deployment on Ks8 - Canary way
## Creatng a new a service account for Kubernetes. 
if [[ $TILLER_ACCOUNT != "tiller" ]]
then
helm init;
echo "$(date): Creating a new KS8 Service Account for the Tiller ..."
kubectl --namespace kube-system create serviceaccount tiller
kubectl --namespace kube-system create clusterrolebinding tiller-cluster-admin --clusterrole=cluster-admin --serviceaccount=kube-system:tiller
kubectl --namespace kube-system patch deploy tiller-deploy -p '{"spec":{"template":{"spec":{"serviceAccount":"tiller"}}}}'
echo "$(date): Waiting for 5 seconds..."
sleep 5;
fi


echo "$(date): Deploying Kafka..."
helm install confluentinc/cp-helm-charts --name my-confluent

echo "$(date): Waiting for 5 seconds..."
sleep 10;


echo "$(date): Deploying Convenio Service..."
helm install -n convenio-app ./charts/convenio/


sleep 10;
echo "$(date): Deploying Convenio Service..."
helm install -n convenio-app ./charts/convenio/
echo "$(date): Deploying Usuario Service..."
helm install -n usuario-app ./charts/usuario
echo "$(date): Deploying Notification Service..."
helm install -n notificacion-app ./charts/notificacion
echo "$(date): Deploying Pagos Service..."
helm install -n pagos-app ./charts/pagos



echo "$(date): Succeed.."

echo "$(date): Please, wait a couple of minutes before using it..."

