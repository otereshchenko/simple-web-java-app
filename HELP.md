# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [minikube start](https://minikube.sigs.k8s.io/docs/start/)
* [additional info](https://github.com/kubernetes/minikube/issues/17288)

> NOTE: The image version and the port are similar in 
> - build.gradle
> - application.yml
> - apps.yaml

### gradle::bootBuildImage

- setup **gradle::bootBuildImage** task
    ```groovy
    bootBuildImage {
      imageName = "xander11m/${project.name}:${version}"
      publish = true
      docker {
          publishRegistry {
              url      = project.property('testDockerSpringContainerRepoUrl')
              username = project.property('testDockerSpringContainerRepoUser')
              password = project.property('testDockerSpringContainerRepoPassword')
          }
      }
  }
    ```

- terminal scrypt for **gradle::bootBuildImage**
    ```shell
  gradle clean bootBuildImage
  ```

- terminal scrypt for **minikube** and setup ingress
    ```shell
  minikube start
  minikube addons enable ingress
  minikube tunnel
  ```

- terminal scrypt for applying deployment on **k8s** 
   ```shell
  kubectl apply -f ./deployment/k8s/apps.yaml
  ```
   ```shell
  kubectl delete -f ./deployment/k8s/apps.yaml
  ```

<p>
v1 shows data fromm 9099 <br/>
v2 shows data from 8080 <br/>
add other paths (for example root path ' / ') to ingress controller to see them
</p>
- check output data 

   ```shell
  curl http://127.0.0.1/v1
  ```
   ```shell
  curl http://127.0.0.1/v2
  ```
  

  
