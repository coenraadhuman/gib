# gib

Gibberish git history analyser, a terminal utility that uses conventional commits to analyse your git history.

### Getting Started

This is a initial release of `gib`, see releases page for supported binaries.

As it stand `gib` supports calculating your current git branch's [semantic version](https://semver.org/)
using all the [conventional commits](https://www.conventionalcommits.org/en/) on that branch:

```bash
$ gib version
0.17.6
```