mod args;
mod version;

use clap::Parser;
use args::Args;
use git2::{Commit, Repository};
use version::Version;

fn main() {
    let args = Args::parse();

    match args.command {
        args::Commands::Version {
            path,
            major,
            minor,
            patch,
            commit_git_hook
        } => {
            let found_path = match path {
                Some(path) => path,
                None => panic!("Repository path not provided"),
            };

            let repo = match Repository::open(found_path) {
                Ok(repository) => repository,
                Err(_) => panic!("Couldn't open repository"),
            };

            let mut revwalk = match repo.revwalk() {
                Ok(found_revwalk) => found_revwalk,
                Err(_) => panic!("Couldn't retrieve revwalk for branch"),
            };

            match revwalk.push_head() {
                Ok(_) => {},
                Err(_) => panic!("Couldn't push head"),
            };

            let commits: Vec<Commit> = revwalk.map(|step| {
                let oid = step.unwrap();

                repo.find_commit(oid).unwrap()
            }).collect();

            let mut version = Version { major: 0, minor: 0 , patch: 0 };

            for commit in commits.iter().rev() {
                match commit.message() {
                    Some(message) => {
                        version = version::add_commit_to_version(version.major, version.minor, version.patch, message)
                    },
                    None => {},
                }
            }

            match commit_git_hook {
                Some(message) => {
                    version = version::add_commit_to_version(version.major, version.minor, version.patch, message.as_str())
                },
                None => {},
            }

            if major {
                version = Version::new(version.major + 1, 0, 0);
            }

            if minor {
                version = Version::new(version.major, version.minor + 1, 0);
            }

            if patch {
                version = Version::new(version.major, version.minor, version.patch + 1);
            }

            version.print();

            // calculate version
            // check if additional commit needs to be added
            // final check if major, minor or patch was flagged
        },
    }

}