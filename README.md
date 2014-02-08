SemDiff (Semantic Diff)
======

A semantic diff is a programming language aware diff that keeps the structure of the source code in mind when it computes the difference between two revisions of a source file. It displays the differences between the two files in a summary form so that the programmer can ignore the changes that has no effect on the behaviour of the source code, for example formatting changes, and focus on those that do have an effect on the behaviour of the code.

SemDiff is the base tool that computes a semantic diff between two Java source files. The tool SemDiffPlugin (https://github.com/liloboy/SemDiffPlugin) which is an [Eclipse](http://eclipse.org/) plugin uses SemDiff to provide the usability accompanying SemDiff.

SemDiff uses the [Eclipse Java development tools (JDT)](http://www.eclipse.org/jdt/) to be able to compute a semantic diff.