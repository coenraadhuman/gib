mod args;
mod commands;
mod conventional;
mod git;
mod semantic;
mod tests;

use args::Args;
use clap::Parser;

fn main() {
    let args = Args::parse();

    match args.command {
        args::Commands::Version { path, major, minor, patch, commit_git_hook, scope_filter } => {
            commands::version::run(path, major, minor, patch, commit_git_hook, scope_filter);
        },
        args::Commands::Changelog { path, commit_git_hook, scope_filter } => {
            commands::changelog::run(path, commit_git_hook, scope_filter);
        },
    }

}