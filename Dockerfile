FROM rust:1.78-alpine3.19 as builder

ENV RUSTBACKTRACE 1

RUN \
    apk update && \
    apk upgrade && \
    apk add --no-cache perl musl-dev make

WORKDIR /build

COPY . .

RUN \
    cargo build --target=$(uname -m)-unknown-linux-musl --release && \
    mv "./target/$(uname -m)-unknown-linux-musl/release/gib" /usr/bin/gib

FROM alpine:3.19

COPY --from=builder /usr/bin/gib /usr/bin/gib

ENTRYPOINT [ "gib" ]
CMD [ "--help" ]