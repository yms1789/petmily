/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        white: "#fff",
        dodgerblue: "#1f90fe",
        gray: "#0f1419",
        darkgray: "#a6a7ab",
        slategray: "#5b7083",
        lightslategray: "#8899a6",
      },
      fontFamily: {
        pretendard: "Pretendard",
        "one-mobile-pop-otf": "'ONE Mobile POP OTF'",
      },
      borderRadius: {
        "3xs": "10px",
      },
    },
    fontSize: {
      xl: "20px",
      "5xl": "24px",
      base: "16px",
      "13xl": "32px",
    },
  },
  corePlugins: {
    preflight: false,
  },
};
