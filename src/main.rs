mod args;
mod commands;
mod version;
mod tests;

use args::Args;
use clap::Parser;

fn main() {
    let args = Args::parse();

    match args.command {
        args::Commands::Version {path, major, minor, patch, commit_git_hook } => {
            commands::version::run(path, major, minor, patch, commit_git_hook);
        },
    }

}