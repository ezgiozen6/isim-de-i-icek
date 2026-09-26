// login.js
// Connects login.html's LOG IN button to POST /api/auth/login.
// Only redirects to account.html when the backend confirms success.

document.addEventListener("DOMContentLoaded", function () {
    const loginButton = document.getElementById("login-button");
    const mailInput = document.getElementById("mail");
    const passwordInput = document.getElementById("password");

    if (!loginButton || !mailInput || !passwordInput) {
        console.error("login.js: expected elements not found on page");
        return;
    }

    // Overriding .onclick (a property assignment) replaces the inline
    // onclick="window.location.href='account.html'" set in the HTML,
    // without editing login.html itself.
    loginButton.onclick = handleLogin;

    async function handleLogin() {
        const mail = mailInput.value.trim();
        const password = passwordInput.value;

        clearError();

        if (!mail || !password) {
            showError("Please enter both mail and password.");
            return;
        }

        loginButton.disabled = true;

        try {
            const response = await fetch("/api/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ mail: mail, password: password }),
            });

            let data = null;
            try {
                data = await response.json();
            } catch (parseErr) {
                // no JSON body, fall through to status-based handling
            }

            if (response.ok && data && data.token) {
                // Keys here must match what UserApi.js reads (authHeaders(),
                // getCurrentUserId()): "token" and "userId" specifically —
                // NOT "authToken"/"currentUser".
                localStorage.setItem("token", data.token);
                if (data.user && data.user.id !== undefined) {
                    localStorage.setItem("userId", String(data.user.id));
                }
                window.location.href = "account.html";
            } else {
                const message = (data && data.error) || "Invalid credentials.";
                showError(message);
            }
        } catch (networkErr) {
            console.error("Login request failed:", networkErr);
            showError("Could not reach the server. Please try again.");
        } finally {
            loginButton.disabled = false;
        }
    }

    function showError(message) {
        let errorEl = document.getElementById("login-error");
        if (!errorEl) {
            errorEl = document.createElement("p");
            errorEl.id = "login-error";
            errorEl.style.color = "red";
            loginButton.parentElement.insertAdjacentElement("beforebegin", errorEl);
        }
        errorEl.textContent = message;
    }

    function clearError() {
        const errorEl = document.getElementById("login-error");
        if (errorEl) {
            errorEl.textContent = "";
        }
    }
});
