#!/usr/bin/env bash

bind_directory=$(pwd)
home_m2_directory=$(echo "$HOME/.m2/")
container_name=$(echo "gib-builder")

if [ -f "build.sh" ]; then
    cd ..
    bind_directory=$(pwd)
    cd ./.build
fi

is_container_running=$((docker container inspect -f '{{.State.Running}}' $container_name)  || false)
is_constainer_exited=$((docker container inspect -f '{{.State.Status}}' $container_name)  || false)

if [ "$is_container_running" == "true" ]; then
  docker exec $container_name /build/.build/container-command.sh
else
  if [ "is_constainer_exited" == "exited" ]; then
    docker start $container_name && docker exec $container_name /build/.build/container-command.sh
  else
    docker run \
      -d \
      --name $container_name \
      --mount type=bind,source="$home_m2_directory",target=/repository \
      --mount type=bind,source="$bind_directory",target=/build \
      ghcr.io/graalvm/graalvm-ce:22.3.1 \
      /build/.build/container-alive-command.sh && docker exec $container_name /build/.build/container-command.sh
  fi
fi

sudo chown -R $(whoami):$(whoami) "$bind_directory/target"