#!/usr/bin/env bash

cd /build
./mvnw -Dmaven.repo.local=/repository -Pnative native:compile