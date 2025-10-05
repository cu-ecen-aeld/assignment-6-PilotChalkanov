#!/bin/sh

case "$1" in
    start)
        echo "Start scull"
        /usr/bin/aesdchar_load
        ;;
    stop)
        echo "Stop scull"
        /usr/bin/aesdchar_unload
        ;;
    *)
        echo "Usage: $0 {start|stop}"
    exit 1
esac

exit 0