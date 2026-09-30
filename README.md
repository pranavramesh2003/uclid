<a href="https://travis-ci.org/uclid-org/uclid"><img src="https://travis-ci.org/uclid-org/uclid.svg?branch=master"></a>
![](https://github.com/uclid-org/uclid/workflows/Uclid%20CI/badge.svg)

# About

UCLID5 is an integrated modeling, verification and synthesis tool. UCLID5 is an evolution of the earlier UCLID modeling and verification system. The UCLID project was one of the first to develop satisfiability modulo theories (SMT) solvers and SMT-based verification methods. Here is the original UCLID paper that appeared at CAV 2002:

Randal E. Bryant, Shuvendu K. Lahiri, and Sanjit A. Seshia. <font color="blue">Modeling and Verifying Systems using a Logic of Counter Arithmetic with Lambda Expressions and Uninterpreted Functions.</font> [\[HTML\]](https://people.eecs.berkeley.edu/~sseshia/pubs/b2hd-bryant-cav02.html)
*Proceedings of the 14th International Conference on Computer-Aided Verification (CAV)*, pp. 78–92, LNCS 2404, July 2002.

If you use UCLID5 in your work, please cite the following papers:

Elizabeth Polgreen, Kevin Cheang, Pranav Gaddamadugu, Adwait Godbole, Kevin Laeufer, Shaokai Lin, Yatin A. Manerkar, Federico Mora, and Sanjit A. Seshia. <font color="blue">UCLID5: Multi-Modal Formal Modeling, Verification, and Synthesis.</font> [\[HTML\]](https://people.eecs.berkeley.edu/~sseshia/pubs/b2hd-polgreen-cav22.html)*34th International Conference on Computer Aided Verification (CAV 2022)*, Haifa, Israel. July 2022.


Sanjit A. Seshia and Pramod Subramanyan. <font color="blue">UCLID5: Integrating Modeling, Verification, Synthesis and Learning.</font> [\[HTML\]](https://people.eecs.berkeley.edu/~sseshia/pubs/b2hd-seshia-memocode18.html)
*Proceedings of the 16th ACM-IEEE International Conference on Formal Methods and Models for System Design (MEMOCODE 2018)*, Beijing, China. October 2018.

For questions and feeback please contact elizabeth.polgreen [at] ed.ac.uk.


## Contact us

For bug reports, first preference is for you to file a GitHub issue. For help using UCLID5 in your work, please email uclid@lists.eecs.berkeley.edu



## UCLID5 Tutorial/Publication

The [tutorial document](https://github.com/uclid-org/uclid/blob/master/tutorial/tutorial.pdf) has a gentle introduction to using UCLID5.

A set of tutorial lectures on UCLID5 can be found [here](https://people.eecs.berkeley.edu/~sseshia/uclid5-tutorial/).


## Versions

Get the [latest release](https://github.com/uclid-org/uclid/releases), or get the latest development version `git clone https://github.com/uclid-org/uclid`.

### Current build environment

The repository is currently built and tested with the following toolchain:
- OpenJDK 27 (Homebrew, macOS aarch64)
- sbt 1.11.7
- Scala 2.12.20
- Z3 4.12.2 (Java bindings jar in `lib/`, native binaries bundled in `z3/bin/`)
- ScalaTest 3.2.2

### Recent fixes (September 2026)

- **Forked test JVM environment (`build.sbt`)**: `sbt test` now runs the test suite in a forked JVM that is automatically configured to load the bundled Z3 4.12.2 native libraries (`java.library.path` and `DYLD_LIBRARY_PATH` point at `z3/bin`) and to find `z3`, `cvc5_wait.sh`, `delphi`, and the bundled oracles on its `PATH`. The pinned Z3 4.12.2 in `z3/bin/` takes precedence over any system-wide Z3 installation, so no manual environment setup is required to run the tests.
- **JDK 22+ support**: on JDK 22 and newer, the forked test JVM is launched with `--enable-native-access=ALL-UNNAMED`, since recent JDKs warn about (and will eventually block) restricted native calls such as `System.loadLibrary`. This makes OpenJDK 27 work out of the box.
- **Dependency updates**: Scala upgraded from 2.12.11 to 2.12.20, sbt upgraded to 1.11.7, and the Z3 Java bindings jar (`lib/com.microsoft.z3.jar`) replaced with the one shipping with Z3 4.12.2.
- **`.gitignore`**: the downloaded solver directories (`z3/`, `cvc5/`, `delphi/`) created by the `get-*.sh` setup scripts are now ignored.
- **macOS**: copying the Z3 dylibs for SIP (`setup-z3-macos.sh`) is no longer needed when running tests through SBT; it is only required for the packaged binary run outside SBT.
- **Running the packaged binary**: documented a no-root setup for the `sbt universal:packageBin` binary: `JAVA_OPTS="--enable-native-access=ALL-UNNAMED -Djava.library.path=.../z3/bin"` lets the launcher JVM load the bundled Z3 native libraries directly (bypassing SIP, which strips `DYLD_LIBRARY_PATH` from JVMs launched outside SBT) and silences the JDK 22+ native-access warning. Verified with all tutorial examples passing on OpenJDK 27.

With these changes, all 672 tests pass on OpenJDK 27.

# Installation

## Prerequisites:
To use the prebuilt binaries, UCLID5 requires:
- [Z3 version 4.12.2](https://github.com/Z3Prover/z3/releases/tag/z3-4.12.1) with the Java bindings
- [OpenJDK](https://openjdk.java.net/) version 11 or newer (releases 11 through 27 are known to work; the current development and test environment uses OpenJDK 27)

To compile from source, UCLID5 requires all of the above plus:
- [SBT version 1.0 or greater.](https://www.scala-sbt.org/download.html) (the current build uses sbt 1.11.7 and Scala 2.12.20)

The following are optional requirements but several tests will fail without them:
- (optional) [CVC5](https://github.com/cvc5/cvc5) version 1.0.3 is the SyGuS-IF compliant solver used for the synthesis tests.
- (optional) [Delphi](https://github.com/polgreen/delphi) is used for the verification modulo oracles tests.

Note: when running the test suite through SBT, the build automatically configures the forked test JVM to load the Z3 native libraries from `z3/bin/` and to find `z3`, `cvc5_wait.sh`, `delphi`, and the bundled oracles on its `PATH`. In particular, the pinned Z3 4.12.2 in `z3/bin/` takes precedence over any other Z3 installation on your system, as newer Z3 versions produce slightly different counterexample output that some tests depend on.



### Installation of prerequisites on Linux

#### Java
- Install instructions for OpenJDK (version 11 or newer) are available at https://openjdk.java.net/install/
#### SBT (only required to build from source)
- Install instructions for SBT are available at http://www.scala-sbt.org/1.0/docs/Setup.html
#### External solvers
- For easy install of prerequisite solvers on Linux, run the following scripts from the root directory of the UCLID5 source repository. These scripts set up Z3/CVC5/Delphi for use with uclid5. This script will download [Z3 version 4.12.2.](https://github.com/Z3Prover/z3/releases/tag/z3-4.12.1)/[CVC5 1.0.3](https://github.com/cvc5/cvc5/releases/tag/cvc5-1.0.3)/[Delphi](https://github.com/polgreen/delphi/releases/tag/0.1) binaries from GitHub.
~~~
    $ source get-z3-linux.sh
    $ source get-cvc5-linux.sh #(optional but some CI synthesis tests will fail without CVC5)
    $ source get-delphi-linux.sh #(optional but some CI synthesis tests will fail without Delphi)
~~~
- These scripts download the binaries for Z3, CVC5 and Delphi respectively and set up your `PATH` and `LD_LIBRARY_PATH` accordingly.
You may wish to permanently add the following lines to your bash_profile:
~~~
    export PATH=$PATH:/path/to/uclid/z3/bin:/path/to/uclid/cvc5/bin:/path/to/uclid/delphi/bin:/path/to/uclid/oracles
    export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/path/to/uclid/z3/bin
~~~

Alternatively, [Z3](https://github.com/Z3Prover/z3), [CVC5](https://github.com/cvc5/cvc5), and [Delphi](https://github.com/polgreen/delphi) can all be built from source, and instructions can be found on their respective git repositories. If you prefer to build Z3 from source, make sure the Z3/Java interface is enabled in your build (currently by passing `--java` to the `mk_make.py` script).


### Installation of prerequisites on Mac

#### Java
- Install OpenJDK (version 11 or newer) with homebrew: `brew install openjdk` (further instructions are available at https://openjdk.org/install/).

### SBT (only required to build from source)
-  `brew install sbt`

#### External solvers
- For easy install of prerequisites on macOS, run the following scripts from the root directory of the UCLID5 source repository. These scripts set up Z3/CVC5/Delphi for use with uclid5. This script will download [Z3 version 4.12.2.](https://github.com/Z3Prover/z3/releases/tag/z3-4.12.1)/[CVC5 1.0.3](https://github.com/cvc5/cvc5/releases/tag/cvc5-1.0.3)/[Delphi](https://github.com/polgreen/delphi/releases/tag/0.1) binaries from GitHub.
~~~
    $ source get-z3-macos.sh
    $ source get-cvc5-macos.sh #(optional but some CI synthesis tests will fail without CVC5)
    $ source get-delphi-macos.sh #(optional but some CI synthesis tests will fail without Delphi)
~~~
- These scripts add the downloaded binaries to your `PATH` and `LD_LIBRARY_PATH` accordingly. You may wish to permanently add the following lines to your bash_profile:
~~~
    export PATH=$PATH:/path/to/uclid/z3/bin:/path/to/uclid/cvc5/bin:/path/to/uclid/delphi/bin:/path/to/uclid/oracles
~~~
- When building and testing through SBT, no further setup is required: `build.sbt` points the forked test JVM at `z3/bin/` for both the Z3 Java native libraries (`java.library.path` / `DYLD_LIBRARY_PATH`) and the solver executables, so the workaround below is **not** needed for `sbt test`.
- Due to System Integrity Protection, introduced in OS X El Capitan, a JVM launched outside of SBT ignores the user set DYLD_LIBRARY_PATH. Running the packaged UCLID5 binary therefore needs either the `JAVA_OPTS`-based setup or a one-time copy of the Z3 dylibs, both described in [Running the packaged binary](#running-the-packaged-binary).

## Using the Pre-built binaries

Get the [latest release](https://github.com/uclid-org/uclid/releases). The uclid binary is located in the bin/ subdirectory

### Running the packaged binary

The packaged binary launches its own JVM outside of SBT. This has two consequences that were fixed to make the binary run out of the box:

1. On macOS, System Integrity Protection strips `DYLD_LIBRARY_PATH` from JVMs launched outside of SBT, so the JVM cannot find the Z3 native libraries. Fix (no root required): pass `-Djava.library.path` via `JAVA_OPTS`, which the launcher script forwards to the JVM, pointing it directly at the `z3/bin/` directory downloaded by `get-z3-*.sh`.
2. On JDK 22 and newer, the restricted native call (`System.loadLibrary`) used by the Z3 Java bindings triggers warnings that will become an error in a future JDK release. Fix: add `--enable-native-access=ALL-UNNAMED` to `JAVA_OPTS`.

Put together, run the binary as follows:

    $ export JAVA_OPTS="--enable-native-access=ALL-UNNAMED -Djava.library.path=/path/to/uclid/z3/bin"
    $ export PATH=$PATH:/path/to/uclid/uclid-0.9.5/bin:/path/to/uclid/z3/bin:/path/to/uclid/cvc5/bin:/path/to/uclid/delphi/bin:/path/to/uclid/oracles
    $ uclid examples/tutorial/ex1.1-fib-model.ucl

This exact setup is verified on OpenJDK 27 (macOS aarch64).

Alternatively, copy the JNI dynamic link library to /Library/Java/Extensions and the non-JNI dynamic link library to /usr/local/lib (or simply run `./setup-z3-macos.sh` on macOS; if you build Z3 from source these files are found in the build directory):
~~~
    cp /path/to/uclid/z3/bin/libz3.dylib /usr/local/lib
    cp /path/to/uclid/z3/bin/libz3java.dylib /Library/Java/Extensions
~~~

## Compiling uclid5 from source

First download the external solvers as described above (`source get-z3-macos.sh` on macOS or `source get-z3-linux.sh` on Linux, plus the optional CVC5 and Delphi scripts if you want the full test suite to pass).

Then run the following command in the root directory of the UCLID5 repository (note that it is not necessary to run `sbt update` if you already have the correct dependencies installed as per https://github.com/uclid-org/uclid/blob/master/build.sbt. However, running it will do no harm.):

    $ sbt update clean compile "set fork:=true" test

`build.sbt` already enables forking for the test JVM and configures it to find the Z3 native libraries and solver executables, so plain `sbt test` works as well. If compilation and tests pass (or if the only failing tests are due to CVC5 and Delphi not being found), you can build a universal package.

    $ sbt universal:packageBin

This will create uclid/target/universal/uclid-0.9.5.zip, which contains the uclid binary in the bin/ subdirectory (with the updated Z3 4.12.2 Java bindings jar bundled into `lib/`). Unzip this file, and add it to your path.

    $ unzip uclid-0.9.5.zip   # or run ./unpack.sh from the repository root
    $ cd uclid-0.9.5
    $ export PATH=$PATH:$PWD/bin

To actually run the packaged binary, additionally apply the `JAVA_OPTS` and solver `PATH` setup described in [Running the packaged binary](#running-the-packaged-binary).


## Running UCLID

Now you can run uclid using the 'uclid' command. For example:

    $ uclid examples/tutorial/ex1.1-fib-model.ucl

 Some useful commands to know:
 - To print the SMT files use the `-g` flag, e.g., `uclid examples/tutorial/ex1.1-fib-model.ucl -g "filename"` will print the SMT file to SMT files with the prefix `filename`.
 - To run UCLID5 with another solver use the `-s` flag, e.g., `uclid examples/tutorial/ex1.1-fib-model.ucl -s "cvc5 --lang smt2 --produce-models"` will use CVC5 as the back-end solver.

# Directory Structure

This repository consists of the following sub-directories.
 - examples : This contains example uclid5 models. See examples/tutorial for the examples from the tutorial.
 - lib: Libraries on which uclid5 depends (Z3).
 - project: Build scripts.
 - src/main/scala: uclid5 source.
 - src/test/scala: uclid5 test suite.
 - test: test programs for uclid5.
 - tutorial: uclid5 tutorial (with LaTeX source)
 - vim: vim syntax highlighting for uclid5.

# Related Tools

* [chiselucl](https://github.com/uclid-org/chiselucl) allows Chisel models to be converted into UCLID5.
