# gib

Gibberish git history analyser, a terminal utility that uses conventional commits to analyse your git history.

### Getting Started

See [releases](https://github.com/coenraadhuman/gib/releases) for supported binaries.

__Supported commands:__

```bash
Gibberish git history analyser, a terminal utility that uses conventional commits to analyse your git history.

Commands:
  version                   Command to calculate the semantic version based on the conventional commits of the current branch.

Options:
  -h,  --help               Prints help
```

__Version command options:__

```bash
Actions for version

Options:
  -c,  --commit-git-hook    Mechanism to provide the latest commit made to be included in project version calculation.
  -m,  --major              Bump current project version with a major increment.
  -mi, --minor              Bump current project version with a minor increment.
  -p,  --patch              Bump current project version with a patch increment.
  -pa, --path               Specify the path of the git project.
  -h,  --help               Prints help
```

### Docker

A docker image has been made for ease of use on pipelines such as Gitlab.

- [Dockerhub](https://hub.docker.com/repository/docker/coenraadhuman/gib/general) - `docker pull coenraadhuman/gib:latest`
- [Github Packages](https://github.com/coenraadhuman/gib/pkgs/container/gib) - `docker pull ghcr.io/coenraadhuman/gib:latest`

### Further Reading

- [Semantic Version](https://semver.org/)
- [Conventional Commits](https://www.conventionalcommits.org/en/)
- [Conventional Commit Types](https://github.com/semantic-gitlog/semantic-gitlog/blob/master/docs/en-us/fundamentals/commit-types.md)