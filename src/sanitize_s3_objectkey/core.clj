(ns sanitize-s3-objectkey.core
  (:require [sanitize-s3-objectkey.latin-map :as lm]
            [clojure.string :as st]
            [clojure.spec.alpha :as s]))


(def SAFE_CHARACTERS #"[^0-9a-zA-Z! _\\.\\*'\\(\\)\\\-/]")

(defn- replace-latin-characters [value]
  (st/replace value #"[^A-Za-z0-9[\\] ]" #(or (lm/mapping %) %)))

(defn- remove-illegal-characters [value]
  (st/replace value SAFE_CHARACTERS ""))

(defn- is-valid-separator? [separator]
  (and separator (not (re-matches SAFE_CHARACTERS separator))))

(s/def ::separator is-valid-separator?)
(s/def ::object-key (s/or :string string?
                          :num number?))

(defn sanitize
  ([object-key]
   (sanitize object-key "-"))
  ([object-key separator]
   (-> object-key
       str
       st/trim
       replace-latin-characters
       remove-illegal-characters
       (st/replace #" " separator))))

(s/fdef sanitize
  :args (s/alt :single (s/cat :object-key ::object-key)
               :double (s/cat :object-key ::object-key :separator ::separator)))