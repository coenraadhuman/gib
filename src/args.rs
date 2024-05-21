use clap::{Parser, Subcommand};

/// Gibberish git history analyser, a terminal utility that uses conventional commits to analyse your git history
#[derive(Parser, Debug)]
#[command(version, about, long_about = None)]
pub struct Args {
    /// Command to calculate the semantic version based on the conventional commits of the current branch
    #[command(subcommand)]
    pub command: Commands,
}

#[derive(Debug, Subcommand)]
pub enum Commands {
    /// Command to calculate the semantic version based on the conventional commits of the current branch
    Version {
        /// Specify the path of the git project, if not specified current directory will be used
        #[arg(short, long)]
        path: Option<String>,

        /// Bump current project version with a major increment
        #[arg(long, default_value_t = false)]
        major: bool,

        /// Bump current project version with a minor increment
        #[arg(long, default_value_t = false)]
        minor: bool,

        /// Bump current project version with a patch increment
        #[arg(long, default_value_t = false)]
        patch: bool,

        /// Mechanism to provide the latest commit made to be included in project version calculation
        #[arg(short, long, value_name = "COMMIT MESSAGE")]
        commit_git_hook: Option<String>,

        /// Scope Regex filter; provide mechanism for calculating the version of a project withing a monorepo based of a regular expression
        #[arg(short, long, value_name = "SCOPE_REGEX_FILTER")]
        scope_filter: Option<String>,
    },
}
