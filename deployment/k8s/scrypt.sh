#setup minikube ingress
minikube start
minikube addons enable ingress
minikube tunnel

#apply deployment
kubectl apply -f ./deployment/k8s/test/java/apps.yaml
# kubectl delete -f ./deployment/k8s/test/java/apps.yaml

curl http://127.0.0.1:8000/v1
curl http://127.0.0.1:8000/v2
