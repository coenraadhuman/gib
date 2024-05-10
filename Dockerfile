FROM rust:1.78-alpine

WORKDIR /build

COPY . .

RUN apk add --update --no-cache build-base pkgconf zlib-dev musl-dev libressl-dev

ENV RUSTFLAGS='-C target-feature=-crt-static'

RUN \
    cargo install --path . && \
    mv ./target/release/gib /usr/bin && \
    rm -R /build

CMD [ "gib" ]