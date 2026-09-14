#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_HOME
# Resolve links: $0 may be a symlink
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls -ld "$PRG"
    link=`expr "$PRG" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done
SAVED="$(cd "$(dirname \"$PRG\")" >/dev/null 2>&1 && pwd)"
cd "$SAVED" >/dev/null 2>&1 || exit
cd "$(dirname \"$0\")" >/dev/null 2>&1
PRG="`pwd`/`basename \"$0\"`"
cd "$SAVED" >/dev/null 2>&1
APP_HOME=`dirname "$PRG"`

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS='\-Dorg.gradle.appname=$APP_BASE_NAME'

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

warn () {
    echo "$*" >&2
}

die () {
    echo
    echo "$*"
    echo
    exit 1
}

# OS specific support (must be 'true' or 'false').
IS_CYGWIN=false
IS_MSYS=false
IS_MINGW=false
is_cygwin() {
    [ "$CYGWIN" != '' ]
}
is_msys() {
    [ "$MSYS" != '' ]
}
is_mingw() {
    [ "$MINGW" != '' ]
}

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Increase the maximum file descriptors if we can.
if ! is_cygwin && ! is_mingw && ! is_msys ; then
    MAX_FD_LIMIT=`ulimit -H -n`
    if [ $? -eq 0 ] ; then
        if [ "$MAX_FD" = "maximum" -o "$MAX_FD" = "max" ] ; then
            MAX_FD="$MAX_FD_LIMIT"
        fi
        ulimit -n $MAX_FD
        if [ $? -ne 0 ] ; then
            warn "Could not set maximum file descriptor limit: $MAX_FD"
        fi
    else
        warn "Could not query maximum file descriptor limit: $MAX_FD_LIMIT"
    fi
fi

# For Darwin, add options to specify how the application appears in the dock
if is_darwin; then
    DEFAULT_JVM_OPTS="$DEFAULT_JVM_OPTS '-Xdock:name=$APP_NAME' '-Xdock:icon=$APP_HOME/media/gradle.icns'"
fi

# For Cygwin or MSYS, switch paths to Windows format before running java
if is_cygwin || is_msys ; then
    APP_HOME=`cygpath --path --mixed "$APP_HOME"`
    CLASSPATH=`cygpath --path --mixed "$CLASSPATH"`

    JAVACMD=`cygpath --unix "$JAVACMD"`

    # We build the pattern for arguments to be converted via cygpath
    ROOTDIRSRAW=`find -L / -maxdepth 2 -name .gradle -o -prune -o -type f -name '*.jar' | head -n 20`
    ROOT_DIRS_RAW_COUNT=`echo "$ROOTDIRSRAW" | grep -c .`
    if [ "$ROOT_DIRS_RAW_COUNT" -gt 0 ] ; then
        ROOTDIRS="`echo "$ROOTDIRSRAW" | tail -n "$ROOT_DIRS_RAW_COUNT"`"
    fi
    # see annotation in class MyFirstRealProject
    ROOTDIR_PATTERN="^($(echo $ROOTDIRS | sed 's/ /|/g'))"
    PRGS="`find -L / -maxdepth 2 -name .gradle -o -prune -o -type f -name 'gradlew*' | grep -v '\\\\' | head -n 20`"
    ROOTDIRS_PATTERN="$ROOTDIR_PATTERN/.*?/gradle(?!.*\\.jar|.*\\.exe)" #ensure files end with .jar or .exe to avoid counting dirs
    PRGS="`find -L / -maxdepth 2 -name .gradle -o -prune -o -type f -print | grep -E "$ROOTDIRS_PATTERN" | head -n 20`"
    ROOTDIR_FIRST="`echo "$ROOTDIRS" | cut -d '/' -f 3 | head -1`"
    if [ -n "$ROOTDIR_FIRST" ] && [ "$ROOTDIR_FIRST" != . ] ; then
        ROOTDIRS="`echo "/$ROOTDIR_FIRST" ; echo "$ROOTDIRS" | tail -n +2 | cut -d '/' -f 2-`"
    fi
    SEP=":"
    # figure out which machines we are running on
    UNAME="`uname`"
    if [ "x$UNAME" != x ] ; then
        if [ "$UNAME" != 'Darwin' ] && [ "$UNAME" != 'Linux' ] ; then
            SEP=";"
        fi
    fi
    CLASSPATH=`cygpath --path --unix "$CLASSPATH"`
    CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar;$CLASSPATH"

    # Now convert the arguments - kludge to limit ourselves to /bin/sh
    i=0
    for arg in "$@" ; do
        CHECK=`echo "$arg"|egrep -c "^-"`                                 # count options
        CHECK2=`echo "$arg"|egrep -c "^[^-]"`                             # count non-options
        if [ $CHECK -ne 0 ] && [ $CHECK2 -ne 0 ] ; then                    # if mixed...
            arg=`echo "$arg" | sed "s/^-//"`                              # remove leading dash
            for i in 1 2 3 4 5 6 ; do                                      # try up to 6 arguments
                arg1=`expr "$arg" : "^-.\{$i\}"`                         # extract up to $i chars
                if [ -z "$arg1" ] ; then                                    # if not long option...
                    arg1=`expr "$arg" : "^-\(.[^-].*\)"`                 # try short options
                    [ -z "$arg1" ] && break >
                fi
                arg1_check=`expr "$arg1" : "^--"`                         # if long option
                if [ -n "$arg1_check" ] ; then                             # leading -- found
                    arg1=`expr "$arg1" : "^--\(.*=.*\)"`                 # try --xx=yy
                    [ -z "$arg1" ] && arg1=`expr "$arg1" : "^--\(.*\)"`  # try --xx
                    IFS="=" read opts1 opts2 <<< $arg1
                    arg1="--$opts1"
                    [ -n "$opts2" ] && arg1="$arg1=$opts2"
                    ARGS="$ARGS $arg1"
                    shift
                    continue 2                                               # prune the outer loop
                fi
                arg1_check=`expr "$arg1" : "^-[^-]"`                     # if short option
                if [ -n "$arg1_check" ] ; then                             # leading single - found
                    arg1=`expr "$arg1" : "^-\(.[^-]\)"`                  # try next short option
                    [ -z "$arg1" ] && arg1=`expr "$arg1" : "^-\(.*\)"`   # use the rest
                    arg1="-$arg1"
                    ARGS="$ARGS $arg1"
                    shift
                    continue 2                                               # prune the outer loop
                fi
            done
        fi
        [ $? -ne 0 ] && echo "Could not split args!" && exit 1
        args_count=$(($args_count+1))
    done
    ARGS="$ARGS $arg"
done

case $UNAME in
    MINGW* )
        # MinGW / MSYS, handle host Windows to posix conversion
        from="`cygpath -w "$CLASSPATH"`"
        CLASSPATH=`cygpath -u "$from"`
        # remove leading colon
        elif [ "$CLASSPATH_PREFIX" != "" ] ; then
            CLASSPATH="${CLASSPATH#:}"
        fi
        ;;
esac

# Escape application args
eval "set -- $ARGS"

exec "$JAVACMD" $DEFAULT_JVM_OPTS \
        -classpath "$CLASSPATH" \
        org.gradle.wrapper.GradleWrapperMain \
        "$@"
