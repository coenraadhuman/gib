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

RUN \
    apk update && \
    apk upgrade && \
    apk add --no-cache bash sed shadow setpriv && \
    rm /var/cache/apk/* && \
    # Preferred shell on pipelines:
    chsh -s $(which bash) && \
    # Required so we can ensure that gib and bash runs with correct Linux privilege as the mounted repository:
    chmod +s $(which setpriv)

# Avoid Git security since end user is explicity mounting repository:
RUN \
    echo '[safe]' > /etc/gitconfig && \
    echo '    directory = *' /etc/gitconfig

COPY --from=builder /usr/bin/gib /usr/bin/gib
COPY --from=builder /build/.docker/gib-entrypoint.sh /usr/bin/gib-entrypoint.sh

WORKDIR /app

ENTRYPOINT ["gib-entrypoint.sh"]
CMD [ "--help" ]