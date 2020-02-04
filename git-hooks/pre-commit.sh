#!/bin/sh

echo "🤫🤫🤫 Running static analysis and linting Yaaaaaaaaaaaaaaaaaaaaaaaay...."

# Validate Kotlin code with detekt and KtLint before committing
./gradlew ktlintFormat detekt ktlint --daemon

status=$?

if [ "$status" = 0 ] ; then
    echo "😎😎😎 Static analysis found no issues. Proceeding with committing the code ."
    exit 0
else
    echo 1>&2 "😱😱😱 Static analysis found issues you need to fix before pushing."
    exit 1
fi

