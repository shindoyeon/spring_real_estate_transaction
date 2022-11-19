<template>
  <b-container class="mt-3">
    <div class="container">
      <main class="form-signin w-100 m-auto">
        <form id="form-join" method="POST" action="">
          <input type="hidden" name="act" value="join" />
          <h1 class="h3 mb-3 fw-normal" style="text-align: center">Sign Up</h1>

          <div class="form-floating mb-1">
            <input
              type="text"
              class="form-control"
              id="userId"
              name="userId"
              placeholder="ID"
            />
            <label for="userId">ID</label>
          </div>
          <div id="idcheck-result"></div>
          <div class="form-floating mb-1">
            <input
              type="text"
              class="form-control"
              id="username"
              name="userName"
              placeholder="name"
            />
            <label for="username">name</label>
          </div>
          <div class="form-floating mb-1">
            <input
              type="password"
              class="form-control"
              id="userpassword"
              name="userPassword"
              placeholder="Password"
            />
            <label for="userpassword">Password</label>
          </div>
          <div class="form-floating mb-1">
            <input
              type="email"
              class="form-control"
              id="useremail"
              name="userEmail"
              placeholder="email"
            />
            <label for="useremail">email</label>
          </div>
          <div class="form-floating mb-1">
            <input
              type="text"
              class="form-control"
              id="userphone"
              name="userPhone"
              placeholder="phone"
            />
            <label for="userphone">phone</label>
          </div>
          <div class="form-floating mb-3">
            <input
              type="text"
              class="form-control"
              id="userrole"
              name="userRole"
              placeholder="role"
            />
            <label for="userrole">role</label>
          </div>
          <button
            class="w-100 btn btn-lg btn-primary"
            id="btn-join"
            type="button"
          >
            Sign Up
          </button>
        </form>
      </main>
    </div>
  </b-container>
</template>

<script>
export default {
  name: "UserRegister",
};
</script>

<style></style>
