FROM rust:1.78-bookworm as builder

RUN \
    apt update && \
    apt upgrade -y && \
    apt install -y libssl-dev && \
    update-ca-certificates

RUN which openssl

WORKDIR /build

COPY . .

RUN \
    cargo build  --target=$(uname -m)-unknown-linux-gnu --release && \
    mv ./target/$(uname -m)-unknown-linux-gnu/release/gib /usr/bin

FROM debian:bookworm-slim

RUN \
    apt update && \
    apt upgrade -y && \
    apt install -y libssl3 && \
    apt clean && \
    rm -rf /var/cache/apt/archives /var/lib/apt/lists

COPY --from=builder /usr/bin/gib /usr/bin/gib

ENTRYPOINT [ "gib" ]
CMD [ "--help" ]