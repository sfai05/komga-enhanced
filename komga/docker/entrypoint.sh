#!/bin/sh
set -e

# Override the baked-in gallery-dl-komga by setting GALLERY_DL_REPO (owner/repo format).
# Optionally set GALLERY_DL_REF to a branch or tag (default: master).
# Example: -e GALLERY_DL_REPO=myuser/my-fork -e GALLERY_DL_REF=dev
if [ -n "$GALLERY_DL_REPO" ]; then
    REF="${GALLERY_DL_REF:-master}"
    echo "[komga] Installing gallery-dl-komga from ${GALLERY_DL_REPO}@${REF}..."
    pip3 install --break-system-packages --no-cache-dir --force-reinstall \
        "gallery_dl[manga] @ https://github.com/${GALLERY_DL_REPO}/archive/refs/heads/${REF}.tar.gz"
fi

exec "$@"
