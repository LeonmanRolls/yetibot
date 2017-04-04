(ns yetibot.commands.poke
  (:require
    [yetibot.core.models.users :as u]
    [yetibot.core.hooks :refer [cmd-hook]]))

(def ^:private config
  {:objects ["pencil" "needle" "sharp stick" "katana" "bisento"
             "safety pin" "chopstick" "rusty nail" "nine inch nail" "shobo"]
   :locations ["face" "eyeball" "solar plexus" "knee cap" "belly" "spleen"
               "iliohypogastric nerve" "tibial nerve" "jiache (ST6)" "xiangu (ST43)"]})

(defn poke-someone
  "poke <user> # always do this"
  {:yb/cat #{:fun}}
  [{name :match chat-source :chat-source}]
  (if-let [user (u/find-user-like chat-source name)]
    (let [obj (rand-nth (:objects config))
          loc (rand-nth (:locations config))]
      (format "Yetibot pokes %s in the %s with a %s"
              (:name user) loc obj))
    (format "Couldn't find anyone named %s." name)))

(defn do-poking
 "poke # never do this"
  {:yb/cat #{:fun}}
  [_] "You shall not poke Yetibot")

(cmd-hook ["poke" #"^poke$"]
          #"^\w+.*$" poke-someone
          #"^$" do-poking)
