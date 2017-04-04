(ns yetibot.test.models.twitter
  (:require
    [clojure.test :refer :all]
    [yetibot.models.twitter :refer :all]))

;; disabled until clj-http 3.0 issue is fixed
;; https://github.com/dakrone/clj-http/pull/327
#_(deftest test-expanding-urls
  (is
    (= "http://25.media.tumblr.com/7908066b66b51c1b0c30135fd9824e8b/tumblr_mv6r5i7JZR1qa0eq0o1_400.gif"
       (expand-url "http://t.co/DGTAQQ0MQo"))))
