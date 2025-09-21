#!/bin/sh

case "$1" in
    start)
        echo "Starting scull modules"
        cd /lib/modules/$(uname -r)/extra/
        /usr/sbin/scull_load
        ;;
    stop)
        echo "Stopping scull modules"
        cd /lib/modules/$(uname -r)/extra/
        /usr/sbin/scull_unload
        ;;
    *)
        echo "Usage: $0 {start|stop}"
    exit 1
esac

exit 0