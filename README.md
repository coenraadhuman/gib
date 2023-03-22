# gib

Gibberish git history analyser, a terminal utility that uses conventional commits to analyse your git history.

### Getting Started

This is a initial release of `gib`, see releases page for supported binaries.

As it stands `gib` supports calculating your current git branch's [semantic version](https://semver.org/)
using all the [conventional commits](https://www.conventionalcommits.org/en/) on that branch:

```bash
Actions for version

Options:
  -c,  --commit-git-hook    Mechanism to provide the latest commit made to be included in project version calculation.
  -m,  --major              Bump current project version with a major increment.
  -mi, --minor              Bump current project version with a minor increment.
  -p,  --patch              Bump current project version with a patch increment.
  -pa, --path               Specify the path of the git project.
```

### Docker

A docker image has been made for ease of use on pipelines such as Gitlab.
