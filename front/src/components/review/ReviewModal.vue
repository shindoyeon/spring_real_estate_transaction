<template>
  <transition name="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">
          <div class="modal-header">
            <slot name="header"> </slot>
          </div>

          <div class="modal-body">
            <slot name="body">
              <div>
                <div class="border-bottom d-flex pb-2">
                  <h5>{{ houseList[curIndex].apartmentName }}</h5>
                </div>
                <div class="border-bottom d-flex py-2 text-danger">
                  <div class="w-25">추천점수</div>
                  <div>
                    <StarRating
                      v-model="recommendScore"
                      active-color="#dc3545"
                      :show-rating="false"
                      :rounded-corners="true"
                      :star-size="20"
                      :star-points="[
                        23, 2, 14, 17, 0, 19, 10, 34, 7, 50, 23, 43, 38, 50, 36,
                        34, 46, 19, 31, 17,
                      ]"
                    ></StarRating>
                  </div>
                </div>
              </div>
              <div class="pt-2 form-group">
                <label class="form-label">리뷰</label>
                <textarea
                  @keyup.enter="insertReview"
                  v-model="content"
                  class="form-control"
                  rows="4"
                  placeholder="리뷰를 남겨주세요."
                  aria-label="With textarea"
                ></textarea>
              </div>
            </slot>
          </div>

          <div class="modal-footer">
            <slot name="footer">
              <button
                @click="$emit('close')"
                class="btn btn-sm btn-secondary btn-outline"
                data-dismiss="modal"
                type="button"
              >
                취소
              </button>
              <button
                @click="insertReviewOne"
                class="btn btn-sm btn-primary btn-outline"
                data-dismiss="modal"
                type="button"
              >
                등록
              </button>
            </slot>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import { mapActions, mapState } from "vuex";
import StarRating from "vue-star-rating";
// import { apiInstance } from "@/api/index.js";

// const api = apiInstance();
export default {
  name: "ReviewModal",

  data() {
    return {
      recommendScore: 0,
      content: "",
    };
  },

  components: {
    StarRating,
  },

  computed: {
    ...mapState("memberStore", ["userInfo"]),
    ...mapState("dealViewStore", ["houseList", "curIndex"]),
  },

  methods: {
    ...mapActions("reviewStore", ["getReviewList", "insertReview"]),
    insertReviewOne() {
      const houseNo = this.houseList[this.curIndex].aptCode;
      //   console.log(houseNo, this.recommendScore, this.content);
      let params = {
        aptCode: houseNo,
        rating: this.recommendScore,
        content: this.content,
        userId: this.userInfo.userId,
      };
      //   console.log(params);
      this.insertReview(params);
      this.$emit("close");
    },
  },
};
</script>

<style>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
  color: #555555;
}

.modal-container {
  width: 500px;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  font-family: Helvetica, Arial, sans-serif;
}

.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}

.modal-body {
  margin: 20px 0;
}

.modal-default-button {
  float: right;
}

/*
  * The following styles are auto-applied to elements with
  * transition="modal" when their visibility is toggled
  * by Vue.js.
  *
  * You can easily play with the modal transition by editing
  * these styles.
  */

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>
