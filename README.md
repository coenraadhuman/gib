![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103) [![GitHub license](https://img.shields.io/badge/licence-GPL--3.0-blue)](LICENSE) [![PRs Welcome](https://img.shields.io/badge/PRs-welcome-green.svg)](.github/CONTRIBUTING.md)[![Image of github-profile-views-counter](https://github.com/coenraadhuman/github-profile-views-counter/blob/master/svg/613868422/badge.svg)](https://github.com/coenraadhuman/github-profile-views-counter/blob/master/readme/613868422/year.md)![GitHub Downloads (all assets, all releases)](https://img.shields.io/github/downloads/coenraadhuman/gib/total?label=Github%20Downloads)
![Docker Pulls](https://img.shields.io/docker/pulls/coenraadhuman/gib?label=Docker%20Hub%20Pulls)



# gib

Gibberish git history analyser, a terminal utility that uses [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/) to analyse your git history.

### Goal

The aim of the utility is to provide tools for pipelines to facilitate [semantic versioning](https://semver.org/) calculation, changelog generation for a project in an automated fashion using [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/).

### Getting Started

You have the following options to obtain the utility:

- See [releases](https://github.com/coenraadhuman/gib/releases) for pre-built binaries that can be used for your own Dockerfile or directly on a machine.
- Docker images are available at [Dockerhub](https://hub.docker.com/r/coenraadhuman/gib) and [Github Packages](https://github.com/coenraadhuman/gib/pkgs/container/gib) (these are optimised to be as small as possible).
- Otherwise you can build it with [Rust](https://www.rust-lang.org/learn/get-started):

```bash
cargo build --release
./target/release/gib version # This binary can be moved to your path
```

### Features

__Currently supported commands:__

```bash
Gibberish git history analyser, a terminal utility that uses conventional commits to analyse your git history

Usage: gib <COMMAND>

Commands:
  version    Command to calculate the semantic version based on the conventional commits of the current branch
  changelog  Command to generate a changelog markdown file based on the conventional commmits and tags of the current branch
  help       Print this message or the help of the given subcommand(s)

Options:
  -h, --help     Print help
  -V, --version  Print version
```

__Version command options:__

```bash
Command to calculate the semantic version based on the conventional commits of the current branch

Usage: gib version [OPTIONS]

Options:
  -p, --path <PATH>
          Specify the path of the git project, if not specified current directory will be used
      --major
          Bump current project version with a major increment
      --minor
          Bump current project version with a minor increment
      --patch
          Bump current project version with a patch increment
  -c, --commit-git-hook <COMMIT MESSAGE>
          Mechanism to provide the latest commit made to be included in project version calculation
  -s, --scope-filter <SCOPE_REGEX_FILTER>
          Scope Regex filter; provide mechanism for calculating the version of a project withing a monorepo based of a regular expression
  -h, --help
          Print help
```

_Tool for testing scope regular expressions with `fancy-regex` crate: [Rustexp](https://rustexp.lpil.uk/)_

__Changelog command options:__

Intent of this command is to provide a simple changelog that will meet most needs, if you want more customisation have a look at [git-cliff](https://git-cliff.org/).

```bash
Command to generate a simple changelog markdown file based on the conventional commmits and tags of the current branch

Usage: gib changelog [OPTIONS]

Options:
  -p, --path <PATH>
          Specify the path of the git project, if not specified current directory will be used
  -c, --commit-git-hook <COMMIT MESSAGE>
          Mechanism to provide the latest commit made to be included in changelog generation
  -s, --scope-filter <SCOPE_REGEX_FILTER>
          Scope regex filter; provide mechanism for generating a changelog for a specific project within a monorepo based of a regular expression
  -h, --help
          Print help
```

_Tool for testing scope regular expressions with `fancy-regex` crate: [Rustexp](https://rustexp.lpil.uk/)_

### Docker

Some notes regarding using `gib` with docker:

- [Dockerhub](https://hub.docker.com/repository/docker/coenraadhuman/gib/general) - `docker pull coenraadhuman/gib:latest`
- [Github Packages](https://github.com/coenraadhuman/gib/pkgs/container/gib) - `docker pull ghcr.io/coenraadhuman/gib:latest`

Run Example:
```bash
# The default work directory is /app
$ docker run -v $PWD:/app ghcr.io/coenraadhuman/gib:latest version
$ 0.10.1
```

User run example:
```bash
$ docker run --user $UID -v $PWD:/app ghcr.io/coenraadhuman/gib:latest version -p /app
$ 0.10.1
```

### Further Reading

- [Semantic Version](https://semver.org/)
- [Conventional Commits](https://www.conventionalcommits.org/en/)
- [Conventional Commit Types](https://github.com/semantic-gitlog/semantic-gitlog/blob/master/docs/en-us/fundamentals/commit-types.md)
