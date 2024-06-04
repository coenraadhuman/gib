# Github Pipeline Example

An example of how Gib can be used on Github Actions to automate versioning and changelog generation:

```yaml
name: Update Version and Changelog

# Only run this on the main branch:
on:
  push:
    branches:
      - main

jobs:
  update_version_changelog:
    runs-on: ubuntu-latest
    # Allow writing new changelog document:
    permissions:
      contents: write
    steps:
      - name: Checkout Repository
        uses: actions/checkout@v4
        with:
          # Ensure that the branch has all the commits for accurate results:
          fetch-depth: 0

      - name: Calculate Version and Generate Changelog
        uses: addnab/docker-run-action@v3
        with:
          image: ghcr.io/coenraadhuman/gib:latest
          # Bash is included with the image:
          shell: /bin/bash
          options: -v ${{ github.workspace }}:/app
          run : |
            echo "================="
            echo "Calculate Version"
            echo "================="
            calculated_version=$(gib version -p /app)
            echo "Calculated version: $calculated_version"

            echo "======================================"
            echo "Amend Build Tool File With New Version"
            echo "======================================"
            # Sed is included in the image to allow an easy way to update build tool files:
            sed -i '0, /version/s/version.*/version = "'$(echo $calculated_version)'"/' /app/Cargo.toml
            echo "Updated Cargo.toml with new version"

            echo "=================="
            echo "Generate Changelog"
            echo "=================="
            gib changelog -p /app > CHANGELOG.md
            echo "Updated CHANGELOG.md document"

      - name: Commit Amended Changes
        run: |
          git config --global user.name "$(git log -1 --pretty=%an)"
          git config --global user.email "$(git log -1 --pretty=%ae)"
          git add ./Cargo.toml
          git add ./CHANGELOG.md
          git commit --amend --no-edit
          git push -f
```
