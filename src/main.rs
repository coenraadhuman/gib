mod args;
mod command;
mod version;

use args::Args;
use clap::Parser;

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
            command::version::run(path, major, minor, patch, commit_git_hook);
        },
    }

}