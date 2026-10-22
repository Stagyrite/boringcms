
## AI Says: BoringCMS on Minikube

This document describes how to deploy BoringCMS and PostgreSQL onto a local Minikube cluster.

---

# Prerequisites

Verify:

```bash
docker info
kubectl version --client
minikube version
```

Start Minikube:

```bash
minikube start
```

Verify:

```bash
kubectl get nodes
```

Expected:

```text
NAME       STATUS   ROLES           AGE     VERSION
minikube   Ready    control-plane   2d20h   v1.32.5
```

---

# Build the BoringCMS Image

Switch Docker to Minikube:

```bash
eval $(minikube docker-env)
```

Build stagyrite/boringcms:latest:

```bash
docker build --build-context m2="$HOME/.m2" -t stagyrite/boringcms:latest .
```


---

# Kubernetes Manifests

Directory layout:

```text
minikube-kompose/
├── db-deployment.yaml
├── db-service.yaml
├── pgdata-persistentvolumeclaim.yaml
├── tomcat-deployment.yaml
└── tomcat-service.yaml
```

---

# Required Fixes

## db-service.yaml

Change:

```yaml
ports:
  - name: "5433"
    port: 5433
    targetPort: 5432
```

to:

```yaml
ports:
  - name: postgres
    port: 5432
    targetPort: 5432
```

Reason:

Tomcat connects using

## Expost Tomcat at port 8080

First run minikube service tomcat --url, as you might already have access to it.

Tomcat wouldn't be visible because its port defaults to ClusterIP type. Thus add the ```type: NodePort``` just below the 'spec'
in the Tomcat service Yaml file.

You might want to use ClusterIP port forwarding instead. kubectl port-forward service/tomcat 8080:8080


## Helm

helm lint .

% helm install boringcms ./boringcms

NAME: boringcms
LAST DEPLOYED: Fri Sep 25 12:07:48 2026
NAMESPACE: default
STATUS: deployed
REVISION: 1
DESCRIPTION: Install complete
TEST SUITE: None

One can even upgrade a configuration.

helm upgrade boringcms ./boringcms

Release "boringcms" has been upgraded. Happy Helming!

minikube service tomcat --url

It will bring the actual URL, like http://127.0.0.1:62451/


## ArgoCD
proceed as in the book but keep eye on it, as it will fail.


kubectl apply --server-side --force-conflicts -n argocd \
-f https://raw.githubusercontent.com/argoproj/argo-cd/refs/heads/master/manifests/install.yaml

kubectl port-forward svc/argocd-server -n argocd 8080:443

Go to https://localhost:8080/ and enjoy the new view!

% kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d; echo
alternative that works: argocd admin initial-password -n argocd

l: admin p: EcWr0Sxp9x6x090A (or anything else from running the above) URL: https://localhost:8080/

argocd repo add https://github.com/argoproj/argocd-example-apps.git \
--insecure-skip-server-verification
argocd app create guestbook \
--repo https://github.com/argoproj/argocd-example-apps.git \
--path guestbook \
--dest-server https://kubernetes.default.svc \
--dest-namespace default

Sync the app by clicking the button. Wait for it to sync (green hearts will appear).

argocd app get guestbook                     
Name:               argocd/guestbook
Project:            default
Server:             https://kubernetes.default.svc
Namespace:          default
URL:                https://localhost:8080/applications/guestbook
Source:
- Repo:             https://github.com/argoproj/argocd-example-apps.git
  Target:           
  Path:             guestbook
  SyncWindow:         Sync Allowed
  Sync Policy:        Manual
  Sync Status:        Synced to  (8088f4c)
  Health Status:      Healthy

GROUP  KIND        NAMESPACE  NAME          STATUS  HEALTH   HOOK  MESSAGE
Service     default    guestbook-ui  Synced  Healthy        service/guestbook-ui created
apps   Deployment  default    guestbook-ui  Synced  Healthy        deployment.apps/guestbook-ui created

kubectl port-forward svc/guestbook-ui 8081:80

Note that 8080 is already in use.

Access http://localhost:8081/