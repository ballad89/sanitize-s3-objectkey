(ns sanitize-s3-objectkey.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [sanitize-s3-objectkey.core :refer [sanitize]]))


; (deftest invalid
;   (testing "trying to sanitize values that are not strings, not numbers or empty strings results in an error"
;     (is (= "" (sanitize nil)))
;     (is (= "" (sanitize "")))
;     (is (= "" (sanitize "{}")))
;     (is (= "" (sanitize "[]")))
;     (is (= "2" (sanitize "[2]")))))

(deftest valid
  (testing "valid object keys should remain the same"
    (is (= "my.great_photos-2014/jan/myvacation.jpg" (sanitize "my.great_photos-2014/jan/myvacation.jpg")))))


(deftest spaces-removed
  (testing "spaces are removed"
    (is (= "my.great_photos-2014/jan/myvacation.jpg" (sanitize "   my.great_photos 2014/jan/myvacation.jpg    ")))))

(deftest numbers
  (testing "numbers should be converted to strings"
    (is (= "123" (sanitize 123)))))

(deftest special-characters
  (testing "Should be sanitized"
    (is (= "123@@456!-)*_" (sanitize "123#@%$^&@456!-)+=*_")))
    (is (= "aeiou" (sanitize "áêīòü")))
    (is (= "test" (sanitize "#+test")))))



(deftest spaces-replaced
  (testing "spaces are replaced with the provided separator or the default"
    (is (= "test-test-test" (sanitize "test test test")))
    (is (= "test/test/test" (sanitize "test test test" "/")))))

; (deftest not-valid-separator
;   (testing "an error is thrown if the requested separator is not valid"
;     (is (= "" (sanitize "test test test" "|")))
;     (is (= "" (sanitize "test test test" nil)))
;     (is (= "" (sanitize "test test test" "~")))))

