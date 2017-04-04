(ns yetibot.commands.giftv
  (:require
    [clojure.string :as s]
    [yetibot.core.hooks :refer [cmd-hook]]
    [yetibot.core.util.http :refer [fetch]]))

(def endpoint "http://www.gif.tv/gifs/get.php")

(defn gif-uri [gif]
  (format "http://www.gif.tv/gifs/%s.gif" gif))

(defn giftv-cmd
  "giftv # fetch a random gif from gif.tv"
  {:yb/cat #{:fun :gif :img}}
  [_]
  (gif-uri (fetch endpoint)))

(cmd-hook #"giftv"
          _ giftv-cmd)
