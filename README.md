# RSO: Exhibition metadata microservice

## Prerequisites

```bash
docker run -d --name pg-exhibition-metadata -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=exhibition-metadata -p 5432:5432 postgres:13
```

## ETCD

### Local

```bash
docker run -p 2379:2379 --name etcd --volume=/tmp/etcd-data:/etcd-data quay.io/coreos/etcd:latest /usr/local/bin/etcd --name my-etcd-1 --data-dir /etcd-data --listen-client-urls http://0.0.0.0:2379 --advertise-client-urls http://0.0.0.0:2379 --listen-peer-urls http://0.0.0.0:2380 --initial-advertise-peer-urls http://0.0.0.0:2380 --initial-cluster my-etcd-1=http://0.0.0.0:2380 --initial-cluster-token my-etcd-token --initial-cluster-state new --auto-compaction-retention 1 -cors="*"
```

```bash
docker exec etcd etcdctl --endpoints http://0.0.0.0:2379 set environments/dev/services/exhibition-service/1.0.0/config/rest-properties/info-mode false
```

### Online

```bash
kubectl exec --stdin --tty etcd-deployment-5b5bd9f7cb-xbz8g -- etcdctl --endpoints http://0.0.0.0:2379 set environments/dev/services/exhibition-service/1.0.0/config/rest-properties/info-mode false
```
