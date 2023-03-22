FROM ghcr.io/graalvm/graalvm-ce:22.3.1 as builder
WORKDIR /build
ADD ./ ./
RUN ./mvnw -Pnative native:compile

FROM ubuntu:jammy

COPY --from=builder /build/target/gib /usr/local/bin/gib