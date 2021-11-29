const fn = {
  job: "ninja",
  age: "23",
  fight: function () {
    console.log(`${this.job} fight!`);
  },
  hide() {
    console.log(`${this.job} hide on bush`);
  },
};

fn.fight();
fn.hide();
