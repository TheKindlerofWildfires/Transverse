# Transverse

## Getting started with development:
0. Use IntelliJ. No support will be given for anyone not using IntelliJ.
1. If you have already cloned the project, close it in IntelliJ (`File -> Close Project`). 
2. Clone the repository
    - `git clone https://github.com/TheKindlerOfWildfires/Transverse.git`
        - Do not clone it through IntelliJ. It will attempt to reconfigure the project, and fail.
    - If you have already cloned the project, you may need to delete the files first.
3. In IntelliJ, open the project (`File -> Open...`, or `Open` from the splash menu, browse to the cloned location, and click open.)
    - **Really truly** do not let it configure the project. It is already correct.
4. Try running `main.kt`. If you do not have the Kotlin plugin, you may need to install it.
5. Profi- uh... Develop!

# Contributing
- Use `git flow` with default branch naming scheme.
    - Pre-installed on `git for windows`
    - Explanation at `http://nvie.com/posts/a-successful-git-branching-model/`
    - Never commit directly to `master`; avoid direct commits to `develop`.
    - Only publish local `feature` branches when necessary
    - Additionally, use `meta`, `experimental` and personal (ex. `keeper/learn-rendering`) branches in `git flow` style.
        - Create with `git checkout -b <branchtype>/<branch-name> <source-branch>`.
        - Publish (when necessary!) with `git push -u origin <full-branch-name>`.
        - Finish with:
            - `git checkout <source-branch>`
            - `git merge --no-ff <full-branch-name>`
                - `--no-ff` is very important.
            - `git checkout <other-merge-branch>` (optionally)
            - `git merge --no-ff <full-branch-name>` (optionally)
            - `git branch -d <full-branch-name>` (usually)
            - `git push` (usually)
- Before comitting:    
    1. Run tests (with coverage)
         - A `Tests` run configuration is provided.
         - Code coverage
             - Tests should cover everything except IO (including rendering) code.
                - Test IO code as thoroughly as is feasible.
    2. Reformat code
        - right-click `src` -> `Reformat Code`
            - Include subdirectories
            - Optimize imports
            - Only VCS changed text
    3. Code cleanup
        - `Analyze -> Inspect Code -> Uncommited files [all]`
        - Use best judgement about which fixes to apply. Avoid spurious diffs at all costs.
    4. Run tests again.
    - Non-compliant commits will be reverted, with as much `--force` as necessary.
- Adding features
    - Check on design documents; open a \[suggestion\] issue to discussion before beginning work.
        - Unless you're okay with having work rejected. Then feel free to jump right in.
            - Use an `experimental` branch for this, not a `feature`.
    - Do all work in a `feature` branch.
    - Keep scope minimal.
    - Always write tests.
    - High-quality assets can be acquired later. Go for the minimum viable product.
