#!/usr/bin/env bash

bind_directory=$(pwd)
home_m2_directory=$(echo "$HOME/.m2/")

if [ -f "build.sh" ]; then
    cd ..
    bind_directory=$(pwd)
    cd ./.build
fi

docker run \
  --rm \
  --name "gib-builder" \
  --mount type=bind,source="$home_m2_directory",target=/repository \
  --mount type=bind,source="$bind_directory",target=/build \
  ghcr.io/graalvm/graalvm-ce:22.3.1 \
  /build/.build/container-command.sh

sudo chown -R $(whoami):$(whoami) "$bind_directory/target"