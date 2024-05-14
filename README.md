# gib

Gibberish git history analyser, a terminal utility that uses conventional commits to analyse your git history.

### Getting Started

See [releases](https://github.com/coenraadhuman/gib/releases) for supported binaries. Otherwise you can build it with [Rust](https://www.rust-lang.org/learn/get-started):

```bash
cargo build --release
./target/release/gib version # This binary can be moved to your path
```

__Supported commands:__

```bash
Gibberish git history analyser, a terminal utility that uses conventional commits to analyse your git history

Usage: gib <COMMAND>

Commands:
  version  Command to calculate the semantic version based on the conventional commits of the current branch
  help     Print this message or the help of the given subcommand(s)

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
  -h, --help
          Print help
```

### Docker

A docker image has been made for ease of use on pipelines such as Gitlab or Github, see this repository workflows for how you can incoporate gib on your pipeline.

- [Dockerhub](https://hub.docker.com/repository/docker/coenraadhuman/gib/general) - `docker pull coenraadhuman/gib:latest`
- [Github Packages](https://github.com/coenraadhuman/gib/pkgs/container/gib) - `docker pull ghcr.io/coenraadhuman/gib:latest`

Example:
```bash
$ docker run ghcr.io/coenraadhuman/gib:0.10.1 version
$ 0.10.1
```

### Further Reading

- [Semantic Version](https://semver.org/)
- [Conventional Commits](https://www.conventionalcommits.org/en/)
- [Conventional Commit Types](https://github.com/semantic-gitlog/semantic-gitlog/blob/master/docs/en-us/fundamentals/commit-types.md)