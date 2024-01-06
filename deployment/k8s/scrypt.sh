#setup minikube ingress
minikube start
minikube addons enable ingress
minikube tunnel

#apply deployment
kubectl apply -f ./deployment/k8s/apps.yaml
# kubectl delete -f ./deployment/k8s/apps.yaml

curl http://127.0.0.1/v1
curl http://127.0.0.1/v2
