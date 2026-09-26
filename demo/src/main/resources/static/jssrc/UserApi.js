// Shared API helper for talking to the Spring Boot backend.
// Assumes login (POST /api/auth/login) stores the token and user id like:
//   localStorage.setItem("token", data.token);
//   localStorage.setItem("userId", data.user.id);

const API_BASE = ""; // same-origin; change this if the backend lives on a different host/port

function authHeaders() {
    const token = localStorage.getItem("token");
    return token ? { "Authorization": `Bearer ${token}` } : {};
}

async function apiGet(path) {
    const res = await fetch(`${API_BASE}${path}`, {
        headers: { "Accept": "application/json", ...authHeaders() }
    });
    if (!res.ok) {
        throw new Error(`GET ${path} failed: ${res.status}`);
    }
    return res.json();
}

function getCurrentUserId() {
    const id = localStorage.getItem("userId");
    if (!id) throw new Error("Not logged in");
    return id;
}

async function getUser(userId) {
    return apiGet(`/api/users/${userId}`);
}

async function getFollowerCount(userId) {
    const followers = await getFollowers(userId);
    return followers.length;
}

async function getFollowingCount(userId) {
    const following = await getFollowing(userId);
    return following.length;
}

async function getPostCountForUser(userId) {
    // Backend has no per-user blog endpoint, so we fetch all blogs and filter client-side
    // rather than changing BlogController.
    const allBlogs = await apiGet(`/api/blogs`);
    return allBlogs.filter(blog => blog.userId === Number(userId)).length;
}

async function getFollowers(userId) {
    // Kept separate from getFollowing on purpose — they hit different endpoints
    // (/api/follows/followers vs /api/follows/following) and return different things.
    return apiGet(`/api/follows/followers?followedId=${userId}`);
}

async function getFollowing(userId) {
    return apiGet(`/api/follows/following?followerId=${userId}`);
}