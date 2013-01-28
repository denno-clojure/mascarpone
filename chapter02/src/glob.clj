(use 'org.satta.glob)

(glob "*.{jpg,gif}")

(glob ".*")  ; dot files are not included by default

(glob "/usr/*/se*")  ; works on directories and subdirectories