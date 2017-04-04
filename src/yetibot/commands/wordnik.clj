(ns yetibot.commands.wordnik
  (:refer-clojure :exclude [read])
  (:require
    [schema.core :as sch]
    [wordnik.api.word :as word]
    [wordnik.api.words :as words]
    [taoensso.timbre :refer [info warn error]]
    [yetibot.core.hooks :refer [cmd-hook]]
    [yetibot.core.config :refer [get-config]]
    [clojure.string :as s]
    [wordnik.core :refer :all]))

(def config (:value (get-config {:key sch/Str} [:wordnik])))

(def ^:private api-key (:key config))

(defmacro ^:private with-auth [& body]
  `(with-api-key api-key ~@body))

(defn- extract-definitions-text [ds]
  (map-indexed (fn [idx d] (str (inc idx) ". " (:text d)))
               ds))

(defn- format-defs [w]
  (conj (extract-definitions-text (:definitions w))
        (:word w)))

(defn define
  "wordnik define <word> # look up the definition for <word> on Wordnik"
  {:yb/cat #{:info}}
  [{[_ w] :match}]
  (with-auth
    (let [ds (word/definitions (s/trim w))
          word (-> ds first :word)]
      (if word
        (conj (extract-definitions-text ds) word)
        (str "No defitions found for " w)))))

(defn random
  "wordnik random # look up a random word on Wordnik"
  {:yb/cat #{:info}}
  [_] (with-auth (define (:word (words/random-word)))))

(defn wotd
  "wordnik wotd # look up the Word of the Day on Wordnik"
  {:yb/cat #{:info}}
  [_] (with-auth (format-defs (words/wotd))))

(cmd-hook #"wordnik"
          #"define\s(\w+)" define
          #"random" random
          #"wotd" wotd)
