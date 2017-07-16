<!DOCTYPE html>
<script>
    var token = '{{ csrf_token() }}';
    var url = '{{ config('app.url') }}/api/v1/opinion/register'
    var data = '{"{{ \App\Models\Opinion::USER_ID }}" : "1", "{{ \App\Models\Opinion::OPINION_MESSAGE }}" : "テストメッセージ", "{{ \App\Models\Opinion::LAT }}" : 35.014229, "{{ \App\Models\Opinion::LON }}" : 135.748218}';
    var request = new XMLHttpRequest();
    request.open('POST', url);
    request.setRequestHeader('X-CSRF-TOKEN', token);
    request.onreadystatechange = function () {
        console.log(request.responseText);
    };
    request.setRequestHeader('Content-Type', 'application/json');
    request.send(data);
</script>
</html>