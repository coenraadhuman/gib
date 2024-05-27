#!/usr/bin/env bash

# Some pipelines insist on running the entrypoint as root inside the container
# even though when a non-privileged runs it on their own files. The below checks
# the work directory and changes the user and group when gib is executed to be the
# same as the ownership of work directory.

set -euo pipefail

if [ "$(id -u)" -ne "$(stat -c '%u' .)" ]; then
  eids="$(stat -c '--euid %u --egid %g' .)"
fi

exec ${eids:+setpriv --clear-groups $eids} real-gib $@